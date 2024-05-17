package comatching.comatcing.process_ai;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import comatching.comatcing.comatching.dto.MatchReq;
import comatching.comatcing.process_ai.match_options.AgeOption;
import comatching.comatcing.process_ai.match_options.ContactFrequencyOption;
import comatching.comatcing.user.entity.UserInfo;
import comatching.comatcing.user.enums.Hobby;

@Component
public class CSVHandler {

	public final String path = "C:\\Users\\smdmi\\Desktop\\comatching.csv";

	public void addUser(UserInfo userInfo) {
		try {
			CSVWriter csvWriter = new CSVWriter(new FileWriter(path, true));
			String[] newData = userAiFeatureToStringArray(userInfo);
			csvWriter.writeNext(newData);
			csvWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void match(MatchReq matchReq, String username) {

		try {
			CSVReader csvReader = new CSVReader(new FileReader(path));
			List<String[]> csvData = csvReader.readAll();
			csvReader.close();

			for (int i = 0; i < csvData.size(); i++) {
				String[] row = csvData.get(i);
				if (row[0].equals(username)) {
					csvData.set(i, setMatchOption(row, matchReq));
					break;
				}
			}

			CSVWriter csvWriter = new CSVWriter(new FileWriter(path));
			csvWriter.writeAll(csvData);
			csvWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CsvException e) {
			e.printStackTrace();
		}
	}

	public void deleteUser(String username) throws IOException, CsvException {
		try {
			CSVReader csvReader = new CSVReader(new FileReader(path));
			List<String[]> csvData = csvReader.readAll();
			csvReader.close();

			for (int i = 0; i < csvData.size(); i++) {
				String[] row = csvData.get(i);
				if (row[0].equals(username)) {
					csvData.remove(i);
					break;
				}
			}

			CSVWriter csvWriter = new CSVWriter(new FileWriter(path));
			csvWriter.writeAll(csvData);
			csvWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CsvException e) {
			e.printStackTrace();
		}
	}

	private String[] userAiFeatureToStringArray(UserInfo userInfo) {

		String[] result = {
			userInfo.getUsername(),
			userInfo.getUserAiFeature().getGender().getVector().toString(),
			userInfo.getUserAiFeature().getAge().toString(),
			userInfo.getUserAiFeature().getMbti(),
			userInfo.getUserAiFeature().getMajor().getVector().toString(),
			Hobby.toCsvValue(userInfo.getUserAiFeature().getHobby()),
			userInfo.getUserAiFeature().getContactFrequency().getVector().toString()
		};

		return result;
	}

	private String[] setMatchOption(String[] row, MatchReq req) {
		row[7] = req.getMbtiOption();
		row[8] = Hobby.toCsvValue(req.getHobby());
		row[9] = AgeOption.toCsvValue(req.getAgeOption());
		row[10] = ContactFrequencyOption.toCsvValue(req.getContactFrequencyOption());
		row[11] = req.getMajorOptionCheck() ? "1" : "0";
		row[12] = "1";
		return row;
	}
}
