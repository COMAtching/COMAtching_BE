package comatching.comatching.process_ai;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import com.opencsv.exceptions.CsvException;

import comatching.comatching.comatching.entity.ComatchHistory;
import comatching.comatching.process_ai.match_options.ContactFrequencyOption;
import comatching.comatching.user.entity.UserInfo;
import comatching.comatching.user.enums.Hobby;
import comatching.comatching.user.repository.UserInfoRepository;
import comatching.comatching.comatching.dto.MatchReq;
import comatching.comatching.comatching.repository.ComatchHistoryRepository;
import comatching.comatching.process_ai.match_options.AgeOption;

@Component
public class CSVHandler {

	private UserInfoRepository userInfoRepository;
	private ComatchHistoryRepository comatchHistoryRepository;

	@Value("${spring.file.path}")
	private String path;

	private Lock lock;

	CSVHandler(UserInfoRepository userInfoRepository, ComatchHistoryRepository comatchHistoryRepository) {
		this.userInfoRepository = userInfoRepository;
		this.comatchHistoryRepository = comatchHistoryRepository;
	}

	public void addUser(UserInfo userInfo) {
		try {
			ICSVWriter csvWriter = new CSVWriterBuilder(new FileWriter(path, true))
				.withQuoteChar(ICSVWriter.NO_QUOTE_CHARACTER)
				.build();
			String[] newData = userAiFeatureToStringArray(userInfo, 1);
			csvWriter.writeNext(newData);
			csvWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void match(MatchReq matchReq, String pickerUsername) {

		try {
			System.out.println("[csvMatch mbti] - " + matchReq.getMbtiOption());
			System.out.println("[csvMatch hobby] - " + matchReq.getHobbyOption().toArray());
			System.out.println("[csvMatch ageOption] - " + matchReq.getAgeOption());
			System.out.println("[csvMatch contactFrequnecy] - " + matchReq.getContactFrequencyOption());

			CSVReader csvReader = new CSVReader(new FileReader(path));
			List<String[]> csvData = csvReader.readAll();
			csvReader.close();

			for (int i = 0; i < csvData.size(); i++) {
				String[] row = csvData.get(i);
				if (row[0].equals(pickerUsername)) {
					String[] s = setMatchOption(row, matchReq);
					System.out.println(setMatchOption(row, matchReq));
					for(int j = 0; j<s.length;j++){
						System.out.println(s[j].toString());
					}

					System.out.println("[csvMatch matcherId] - " + i);
					csvData.set(i, setMatchOption(row, matchReq));
					break;
				}
			}
			ICSVWriter csvWriter = new CSVWriterBuilder(new FileWriter(path))
				.withQuoteChar(ICSVWriter.NO_QUOTE_CHARACTER)
				.build();
			csvWriter.writeAll(csvData);
			csvWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CsvException e) {
			e.printStackTrace();
		}
	}

	public void addPickMeCount(String username, Integer pickMe) {
		try {
			CSVReader csvReader = new CSVReader(new FileReader(path));
			List<String[]> csvData = csvReader.readAll();
			csvReader.close();

			for (int i = 0; i < csvData.size(); i++) {
				String[] row = csvData.get(i);
				if (row[0].equals(username)) {

					System.out.println("[CSVHandler] - row[13] = " + row[13]);
					Integer count = Integer.parseInt(row[13]);
					if (count < 0) {
						count = 0;
					}
					count += pickMe;
					//count -= 1;
					row[13] = count.toString();
					csvData.set(i, row);
					break;
				}
			}

			ICSVWriter csvWriter = new CSVWriterBuilder(new FileWriter(path))
				.withQuoteChar(ICSVWriter.NO_QUOTE_CHARACTER)
				.build();
			csvWriter.writeAll(csvData);
			csvWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CsvException e) {
			e.printStackTrace();
		}
	}

	private String[] userAiFeatureToStringArray(UserInfo userInfo, Integer pickMe) {
		String[] result = {
			userInfo.getUsername(),
			userInfo.getUserAiFeature().getGender().getVector().toString(),
			userInfo.getUserAiFeature().getAge().toString(),
			userInfo.getUserAiFeature().getMbti(),
			userInfo.getUserAiFeature().getMajor().getVector().toString(),
			Hobby.toCsvValue(userInfo.getUserAiFeature().getHobby()),
			userInfo.getUserAiFeature().getContactFrequency().getVector().toString(),
			"", "", "", "", "", "",
			pickMe.toString(), ""
		};
		return result;
	}

	public void syncCsv() {
		try {
			ICSVWriter csvWriter = new CSVWriterBuilder(new FileWriter(path))
				.withQuoteChar(ICSVWriter.NO_QUOTE_CHARACTER)
				.build();
			List<UserInfo> userInfoList = userInfoRepository.findAllUsers();
			List<String[]> csvData = new ArrayList<>();
			String csvHeader = "idx,gender,age,mbti,major,hobby,contact_frequency,ageOption,mbtiOption,majorOption,hobbyOption,contactFrequencyOption,isRquest,pickMe,duplicationUsers";
			String[] headerArray = csvHeader.split(",");
			csvData.add(headerArray);

			for (UserInfo userInfo : userInfoList) {
				System.out.println(userInfo.getUsername());
				String[] row = userAiFeatureToStringArray(userInfo, userInfo.getPickMe());
				String duplication;
				List<ComatchHistory> comatchHistoryList = comatchHistoryRepository.findByUserInfoUsername(
					userInfo.getUsername());

				if (comatchHistoryList.isEmpty()) {
					duplication = "";
				} else {
					// username들을 _로 연결
					duplication = comatchHistoryList.stream()
						.map(history -> history.getUserInfo().getUsername())
						.collect(Collectors.joining("_"));
				}
				row[row.length - 1] = duplication;

				csvData.add(row);
			}

			csvWriter.writeAll(csvData);
			csvWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String[] setMatchOption(String[] row, MatchReq req) {
		row[7] = AgeOption.toCsvValue(req.getAgeOption());
		row[8] = req.getMbtiOption();
		row[9] = req.getNoSameMajorOption() ? "1" : "0";
		row[10] = Hobby.toCsvValue(req.getHobbyOption());
		row[11] = ContactFrequencyOption.toCsvValue(req.getContactFrequencyOption());
		row[12] = "1";
		return row;
	}
}
