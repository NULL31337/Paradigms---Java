import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class WordStatCountShingles {
	public static void put(String pt, Map <String, Integer> linkedHashMap) {
		for (int i = 0; i <= pt.length() - 3; i++) {
			String subString = pt.substring(i, i + 3);
			linkedHashMap.put(subString, linkedHashMap.getOrDefault(subString, 0) + 1);
		}
	}
	
	public static void main(String[] args) {
		String inputFile = args[0];		
		String outputFile = args[1];
		Map <String, Integer> linkedHashMap = new LinkedHashMap<> ();
		try (BufferedReader reader = new BufferedReader (
			new InputStreamReader(new FileInputStream(inputFile),"utf8"))) {
			Scanner scanner = new Scanner (reader);
			scanner.typeOfSeparator = 0;
			while (scanner.hasNext()) {
			    while (scanner.hasNextLine());
			    if (scanner.hasNext()) {
                    String key = scanner.getNext();
                    put(key, linkedHashMap);
                }
			}
		} catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException: "  + e.getMessage());
            return;
        } catch (SecurityException e) {
            System.out.println("SecurityException: " + e.getMessage());
            return;
        } catch (IOException e) {
            System.out.println("Error reading input file: "  + e.getMessage());
            return;
        }

		List <Map.Entry <String, Integer> > unsortedAnswer =  new ArrayList <> (linkedHashMap.entrySet());
		unsortedAnswer.sort(Map.Entry.comparingByValue());

		try (OutputStreamWriter writer =
            new OutputStreamWriter(new FileOutputStream(outputFile),"utf8")) {
			for (Map.Entry <String, Integer> answer : unsortedAnswer) {
                writer.write(answer.getKey() + " " + answer.getValue() + System.lineSeparator());
            }
		} catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException: "  + e.getMessage());
        } catch (SecurityException e) {
            System.out.println("SecurityException: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error IOException occurred" + e.getMessage());
        }
	}
}