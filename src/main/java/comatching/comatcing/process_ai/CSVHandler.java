package comatching.comatcing.process_ai;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import com.opencsv.exceptions.CsvException;

import comatching.comatcing.comatching.dto.MatchReq;
import comatching.comatcing.process_ai.match_options.AgeOption;
import comatching.comatcing.process_ai.match_options.ContactFrequencyOption;
import comatching.comatcing.user.entity.UserInfo;
import comatching.comatcing.user.enums.Hobby;

@Component
public class CSVHandler {

	@Value("${spring.file.path}")
	private String path;

	public void addUser(UserInfo userInfo) {
		try {
			ICSVWriter csvWriter = new CSVWriterBuilder(new FileWriter(path, true))
				.withQuoteChar(ICSVWriter.NO_QUOTE_CHARACTER)
				.build();
			String[] newData = userAiFeatureToStringArray(userInfo);
			csvWriter.writeNext(newData);
			csvWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void match(MatchReq matchReq, String pickerUsername) {

		try {
			CSVReader csvReader = new CSVReader(new FileReader(path));
			List<String[]> csvData = csvReader.readAll();
			csvReader.close();

			for (int i = 0; i < csvData.size(); i++) {
				String[] row = csvData.get(i);
				if (row[0].equals(pickerUsername)) {
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

	public void updatePickMeCount(String username) {
		try {
			CSVReader csvReader = new CSVReader(new FileReader(path));
			List<String[]> csvData = csvReader.readAll();
			csvReader.close();

			for (int i = 0; i < csvData.size(); i++) {
				String[] row = csvData.get(i);
				if (row[0].equals(username)) {

					System.out.println("[CSVHandler] - row[13] = " + row[13]);
					Integer count = Integer.parseInt(row[13]);
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
	/*public void deleteUser(String username) throws IOException, CsvException {
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

			ICSVWriter csvWriter = new CSVWriterBuilder(new FileWriter(path, true))
				.withQuoteChar(ICSVWriter.NO_QUOTE_CHARACTER)
				.build();
			csvWriter.writeAll(csvData);
			csvWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CsvException e) {
			e.printStackTrace();
		}
	}*/

	private String[] userAiFeatureToStringArray(UserInfo userInfo) {

		String[] result = {
			userInfo.getUsername(),
			userInfo.getUserAiFeature().getGender().getVector().toString(),
			userInfo.getUserAiFeature().getAge().toString(),
			userInfo.getUserAiFeature().getMbti(),
			userInfo.getUserAiFeature().getMajor().getVector().toString(),
			Hobby.toCsvValue(userInfo.getUserAiFeature().getHobby()),
			userInfo.getUserAiFeature().getContactFrequency().getVector().toString(),
			"", "", "", "", "", "",
			"1"
		};
		return result;
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
