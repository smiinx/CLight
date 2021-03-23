package neon;

import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Random;

import org.bukkit.command.CommandSender;
import org.bukkit.material.Step;

import net.minecraft.server.v1_12_R1.Item;
import net.minecraft.server.v1_12_R1.ItemStack;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.ReportedException;

/**
 * The Core of the CLight Library. This interface
 * has several separations by Categories. These
 * are shown as classes.
 * @author  zNeon
 * @since   JDK8.0
 */
public interface CLight {

	/**
	 * Things that CLight can do with Minecraft.
	 * @author zNeon
	 */
	public static interface Minecraft{
		public static class Bukkit{
			public static void stopServer(){
				org.bukkit.Bukkit.dispatchCommand(org.bukkit.Bukkit.getConsoleSender(), "stop");
			}
			public static void executeConsoleCommand(String command){
				org.bukkit.Bukkit.dispatchCommand(org.bukkit.Bukkit.getConsoleSender(), command);
			}
			public static void executeCommand(CommandSender sender, String command){
				org.bukkit.Bukkit.dispatchCommand(sender, command);
			}
		}


	}
	/**
	 * In this class you can generate stuff.
	 * @author zNeon
	 */
	public static class Generate {
		public static int generateRandomInt(int max) {
			Random rand = new Random();
			int finalint = rand.nextInt(max); 
			return finalint;
		}
		public static String generateRandomString(int stringLength, boolean setLength, boolean alphanumeric, boolean capsulated) {
			String result = "";
			int length = stringLength;
			if(!setLength) {
				length = generateRandomInt(stringLength);
				if(length == 0) {
					length++;
				}
			}

			if(alphanumeric) {
				int leftLimit = 48;
				int rightLimit = 122;
				Random random = new Random();

				result = random.ints(leftLimit, rightLimit + 1)
						.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
						.limit(length)
						.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
						.toString();
				String capsulate = result;

				if(capsulated) {
					Random rnd = new Random();
					char[] nameLetters = capsulate.toCharArray();
					for(int i = 0; i < nameLetters.length; i++) {
						if(rnd.nextInt(2) == 1) {
							result += String.valueOf(nameLetters[i]).toUpperCase();
						}else {
							result += String.valueOf(nameLetters[i]).toLowerCase();
						}
					}
				}
			}else {

				int leftLimit = 97;
				int rightLimit = 122;
				Random random = new Random();
				StringBuilder buffer = new StringBuilder(length);
				for (int i = 0; i < length; i++) {
					int randomLimitedInt = leftLimit + (int) 
							(random.nextFloat() * (rightLimit - leftLimit + 1));
					buffer.append((char) randomLimitedInt);
				}
				result = buffer.toString();
				String capsulate = result;
				if(capsulated) {
					Random rnd = new Random();
					char[] nameLetters = capsulate.toCharArray();
					for(int i = 0; i < nameLetters.length; i++) {
						if(rnd.nextInt(2) == 1) {
							result += String.valueOf(nameLetters[i]).toUpperCase();
						}else {
							result += String.valueOf(nameLetters[i]).toLowerCase();
						}
					}
				}
			}
			return result;
		}
		public static int generateRandomIntChain(int numLength, boolean setLength) throws Exception {
			int finres = 0;
			if(numLength > 300) {
				String crashlog = "The length shouldn't be longer than 300!";
				throw new Exception(crashlog);
			}else {
				String result = "";
				int length = numLength;
				if(!setLength) {
					length = generateRandomInt(numLength);
					if(length == 0) {
						length++;
					}
				}

				int leftLimit = 48;
				int rightLimit = 57;
				Random random = new Random();

				result = random.ints(leftLimit, rightLimit + 1)
						.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
						.limit(length)
						.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
						.toString();


				int intresult = Integer.parseInt(result);
				finres = intresult;
			}
			return finres;
		}


	}
	/**
	 * Some helpful things with Files.
	 * @author zNeon
	 */
	public static class FileManager{
		public static String getHomeDirectory() {
			String output = System.getProperty("user.home");
			return output;
		}
		public static void copyFile(String oldFile, String newFile) throws IOException {
			File old = new File(oldFile);
			File newF = new File(newFile);
			String write = getFileContent(old);
			writeToFile(newF, write);
		}
		public static String getFileContent(File file) throws IOException{
			String filepath = file.getPath();
			String readed = new String ( Files.readAllBytes( Paths.get(filepath) ) );
			return readed;
		}
		public static void writeToFile(File file, String write) throws IOException{
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(write);
			writer.close();
		}
		public static void cryptFile(File file, int key) throws IOException {
			String filecontent = getFileContent(file);
			Utils.encryptString(filecontent, key);
			writeToFile(file, filecontent);
			cleanupFile(file);
		}
		public static void cleanupFile(File file) throws IOException {
			String filepath = file.getPath();
			String readed = new String ( Files.readAllBytes( Paths.get(filepath) ) );
			File newFile = new File(file.getPath().replace(".txt", " new.txt"));
			if(newFile.exists()) {
				newFile.delete();
			}
			readed = readed.replaceAll("\n", "");
			readed = readed.replaceAll("	", "");
			
			int index = readed.indexOf(" ");
			char charSpace = readed.charAt(index);
			
			for(int i = 0; i < readed.length(); i++) {
				char c = readed.charAt(i);
				String stringSpace = Character.toString(c);
				if(stringSpace.equalsIgnoreCase("")) {
					char charSpaceBefore = readed.charAt(i -1);
					char charSpaceAfter = readed.charAt(i +1);
					String stringSpace1 = Character.toString(charSpaceBefore);
					String stringSpace2 = Character.toString(charSpaceAfter);
					if(stringSpace1.equalsIgnoreCase(";") || stringSpace2.equalsIgnoreCase(";")) {
						readed = readed.replaceAll(stringSpace, "");
					}
					if(stringSpace1.equalsIgnoreCase("+") || stringSpace2.equalsIgnoreCase("+")) {
						readed = readed.replaceAll(stringSpace, "");
					}
					if(stringSpace1.equalsIgnoreCase("-") || stringSpace2.equalsIgnoreCase("-")) {
						readed = readed.replaceAll(stringSpace, "");
					}
					if(stringSpace1.equalsIgnoreCase("*") || stringSpace2.equalsIgnoreCase("*")) {
						readed = readed.replaceAll(stringSpace, "");
					}
					if(stringSpace1.equalsIgnoreCase("/") || stringSpace2.equalsIgnoreCase("/")) {
						readed = readed.replaceAll(stringSpace, "");
					}
					if(stringSpace1.equalsIgnoreCase("(") || stringSpace2.equalsIgnoreCase("(")) {
						readed = readed.replaceAll(stringSpace, "");
					}
					if(stringSpace1.equalsIgnoreCase(")") || stringSpace2.equalsIgnoreCase(")")) {
						readed = readed.replaceAll(stringSpace, "");
					}
					if(stringSpace1.equalsIgnoreCase("{") || stringSpace2.equalsIgnoreCase("{")) {
						readed = readed.replaceAll(stringSpace, "");
					}
					if(stringSpace1.equalsIgnoreCase("}") || stringSpace2.equalsIgnoreCase("}")) {
						readed = readed.replaceAll(stringSpace, "");
					}
					if(stringSpace1.equalsIgnoreCase("[") || stringSpace2.equalsIgnoreCase("[")) {
						readed = readed.replaceAll(stringSpace, "");
					}
					if(stringSpace1.equalsIgnoreCase("]") || stringSpace2.equalsIgnoreCase("]")) {
						readed = readed.replaceAll(stringSpace, "");
					}
					if(stringSpace1.equalsIgnoreCase("=") || stringSpace2.equalsIgnoreCase("=")) {
						readed = readed.replaceAll(stringSpace, "");
					}
					if(stringSpace1.equalsIgnoreCase(" ") || stringSpace2.equalsIgnoreCase(" ")) {
						readed = readed.replaceAll(stringSpace, "");
					}
					if(stringSpace1.equalsIgnoreCase(",") || stringSpace2.equalsIgnoreCase(",")) {
						readed = readed.replaceAll(stringSpace, "");
					}
					if(stringSpace1.equalsIgnoreCase("<") || stringSpace2.equalsIgnoreCase("<")) {
						readed = readed.replaceAll(stringSpace, "");
					}
					if(stringSpace1.equalsIgnoreCase(">") || stringSpace2.equalsIgnoreCase(">")) {
						readed = readed.replaceAll(stringSpace, "");
					}
					if(stringSpace1.equalsIgnoreCase(" ") || stringSpace2.equalsIgnoreCase(" ")) {
						readed = readed.replaceAll(stringSpace, "");
					}
				}
				
				
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));
			writer.write(readed);
			writer.close();
		}
		
	}
	/**
	 * Other stuff, that doesn't have a special category.
	 * @author zNeon
	 */
	public static class Utils {
		public static void copyToClipboard(String toCopy) {
			StringSelection stringSelection = new StringSelection(toCopy);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(stringSelection, null);
		}
		public static String encryptString(String str, int key) {
			if(key > 25) {
				String crashlog = "Key may not be larger than 25";
				throw new IndexOutOfBoundsException(crashlog);
			}else {
				str = str.toLowerCase();
				String[] stralphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
				char[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'Ø', 'ƒ', '§', 'á', '%', 'Ñ', '®', '«', '»', '©', '╣', '¬', '”', '┬', '£', 'ﬁ', '╚', '˜', '·', '¯', '¤', '˙', 'ð', 'Ð', 'Ê', '▄', 'Ø', 'ƒ', '§', 'á', '%', 'Ñ', '®', '«', '»', '©', '╣', '¬', '”', '┬', '£', 'ﬁ', '╚', '˜', '·', '¯', '¤', '˙', 'ð', 'Ð', 'Ê', '▄'};
				String result = str;
				key--;
				str = str.replace('a', alphabet[1 + key]);
				str = str.replace('b', alphabet[2 + key]);
				str = str.replace('c', alphabet[3 + key]);
				str = str.replace('d', alphabet[4 + key]);
				str = str.replace('e', alphabet[5 + key]);
				str = str.replace('f', alphabet[6 + key]);
				str = str.replace('g', alphabet[7 + key]);
				str = str.replace('h', alphabet[8 + key]);
				str = str.replace('i', alphabet[9 + key]);
				str = str.replace('j', alphabet[10 + key]);
				str = str.replace('k', alphabet[11 + key]);
				str = str.replace('l', alphabet[12 + key]);
				str = str.replace('m', alphabet[13 + key]);
				str = str.replace('n', alphabet[14 + key]);
				str = str.replace('o', alphabet[15 + key]);
				str = str.replace('p', alphabet[16 + key]);
				str = str.replace('q', alphabet[17 + key]);
				str = str.replace('r', alphabet[18 + key]);
				str = str.replace('s', alphabet[19 + key]);
				str = str.replace('t', alphabet[20 + key]);
				str = str.replace('u', alphabet[21 + key]);
				str = str.replace('v', alphabet[22 + key]);
				str = str.replace('w', alphabet[23 + key]);
				str = str.replace('x', alphabet[24 + key]);
				str = str.replace('y', alphabet[25 + key]);
				str = str.replace('z', alphabet[26 + key]);
				str = str.replace(' ', '…');
				str = str.replace('	', '⌃');
			}
			return str.toLowerCase();
		}
		public static String decryptString(String str, int key) {
			if(key > 25) {
				String crashlog = "Key may not be larger than 25";
				throw new IndexOutOfBoundsException(crashlog);
			}else {
			str = str.toLowerCase();
			String[] stralphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
			char[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'Ø', 'ƒ', '§', 'á', '%', 'Ñ', '®', '«', '»', '©', '╣', '¬', '”', '┬', '£', 'ﬁ', '╚', '˜', '·', '¯', '¤', '˙', 'ð', 'Ð', 'Ê', '▄', '¶', 'Ø', 'ƒ', '§', 'á', '%', 'Ñ', '®', '«', '»', '©', '╣', '¬', '”', '┬', '£', 'ﬁ', '╚', '˜', '·', '¯', '¤', '˙', 'ð', 'Ð', 'Ê', '▄', '¶'};
			key++;
			str = str.replace('a', alphabet[27 - key]);
			str = str.replace('b', alphabet[28 - key]);
			str = str.replace('c', alphabet[29 - key]);
			str = str.replace('d', alphabet[30 - key]);
			str = str.replace('e', alphabet[31 - key]);
			str = str.replace('f', alphabet[32 - key]);
			str = str.replace('g', alphabet[33 - key]);
			str = str.replace('h', alphabet[34 - key]);
			str = str.replace('i', alphabet[35 - key]);
			str = str.replace('j', alphabet[36 - key]);
			str = str.replace('k', alphabet[37 - key]);
			str = str.replace('l', alphabet[38 - key]);
			str = str.replace('m', alphabet[39 - key]);
			str = str.replace('n', alphabet[40 - key]);
			str = str.replace('o', alphabet[41 - key]);
			str = str.replace('p', alphabet[42 - key]);
			str = str.replace('q', alphabet[43 - key]);
			str = str.replace('r', alphabet[44 - key]);
			str = str.replace('s', alphabet[45 - key]);
			str = str.replace('t', alphabet[46 - key]);
			str = str.replace('u', alphabet[47 - key]);
			str = str.replace('v', alphabet[48 - key]);
			str = str.replace('w', alphabet[49 - key]);
			str = str.replace('x', alphabet[50 - key]);
			str = str.replace('y', alphabet[51 - key]);
			str = str.replace('z', alphabet[52 - key]);
			str = str.replace('!', alphabet[53 - key]);
			str = str.replace('§', alphabet[55 - key]);
			str = str.replace('$', alphabet[56 - key]);
			str = str.replace('%', alphabet[57 - key]);
			str = str.replace('@', alphabet[62 - key]);
			str = str.replace('¬', alphabet[64 - key]);
			str = str.replace('”', alphabet[65 - key]);
			str = str.replace('#', alphabet[66 - key]);
			str = str.replace('£', alphabet[67 - key]);
			str = str.replace('ﬁ', alphabet[68 - key]);
			str = str.replace('˜', alphabet[70 - key]);
			str = str.replace('·', alphabet[71 - key]);
			str = str.replace('¯', alphabet[72 - key]);
			str = str.replace('˙', alphabet[73 - key]);
			str = str.replace('˙', alphabet[75 - key]);
			str = str.replace('…', ' ');
			str = str.replace('⌃', '	');
			str = str.replace('√', '	');
			return str.toLowerCase();
			}
		}
		public static String readWebsiteContent(String URL) throws IOException {
			String output = "";
			URL url = new URL(URL);
			URLConnection spoof = url.openConnection();
			spoof.setRequestProperty( "User-Agent", "Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0; H010818)" );
			BufferedReader in = new BufferedReader(new InputStreamReader(spoof.getInputStream()));
			String strLine = "";
			while ((strLine = in.readLine()) != null) {
				output += output + strLine;
			}
			return output;
		}
		public static void openWebsite(String URL) {
			URI uri = URI.create(URL);
			try {
				Desktop.getDesktop().browse(uri);
			} catch (IOException e) {
				e.printStackTrace();
			} 
			
		}
		

		
	}
	/**
	 * Things with Math.
	 * @author zNeon
	 */
	public static class Math {
		static boolean checkForPrime(int number) {
			boolean isItPrime = true;

			if(number <= 1)  {
				isItPrime = false;

				return isItPrime;
			}
			else {
				for (int i = 2; i<= number/2; i++)  {
					if ((number % i) == 0) {
						isItPrime = false;

						break;
					}
				}

				return isItPrime;
			}
		}
		static boolean evenCheck(int number) {
			boolean even = false;

			if(String.valueOf(number).endsWith("0")) {
				even = true;
			}else if(String.valueOf(number).endsWith("2")) {
				even = true;
			}else if(String.valueOf(number).endsWith("4")) {
				even = true;
			}else if(String.valueOf(number).endsWith("6")) {
				even = true;
			}else if(String.valueOf(number).endsWith("8")) {
				even = true;
			}

			return even;	
		}
	}
}
