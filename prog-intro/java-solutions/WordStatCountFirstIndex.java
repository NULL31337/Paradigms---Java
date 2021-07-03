import java.io.*;
import java.util.*;

public class WordStatCountFirstIndex {
	public static void main(String[] args) {
		String inputFile = args[0];
		String outputFile = args[1];
		Map <String, IntList> linkedHashMap = new LinkedHashMap <> ();
		try (BufferedReader reader = new BufferedReader (
			new InputStreamReader(new FileInputStream(inputFile), "utf8"))) {//NOTE: read about try-with-resources
			Scanner scanner = new Scanner (reader);
			scanner.typeOfSeparator = 0;
			int curLine = 0;
			while (scanner.hasNext()) {
			    while (scanner.hasNextLine()){
			        curLine++;
                }
			    if (scanner.hasNext()) {
                    String key = scanner.getNext();
                    IntList tmp = linkedHashMap.getOrDefault(key, new IntList());
                    if (tmp.lastElementLine != curLine) {
                        tmp.lastElementLine = curLine;
                        tmp.push_back(scanner.getPosition());
                    }
                    tmp.countOfWords++;
                    linkedHashMap.put(key, tmp);
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
		Set <String> keySetLinkedHashMap = linkedHashMap.keySet();//NOTE: do not need extra Set
        String[] words = new String[keySetLinkedHashMap.size()];
        int k = 0;
        for (String tmp : keySetLinkedHashMap){
            words[k++] = tmp;
        }

        Arrays.sort(words, Comparator.comparingInt(o -> linkedHashMap.get(o).countOfWords));

		try (OutputStreamWriter writer =
            new OutputStreamWriter(new FileOutputStream(outputFile),"utf8")) {
			for (String answer : words) {
                IntList out = linkedHashMap.get(answer);
                writer.write(answer + " " + out.countOfWords);
                for (int i = 0; i < out.size(); i++) {
                    writer.write(" " + out.getEl(i));
                }
                writer.write(System.lineSeparator());
            }
		} catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException: "  + e.getMessage());
        } catch (SecurityException e) {
            System.out.println("SecurityException: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Erred IOException occurred" + e.getMessage());
        }
	}
}