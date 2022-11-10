import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class FilesManagement{

    public static List<File> ListDirectoryFiles(String directoryPath){
        File directory = new File(directoryPath);
        List<File> directoryFiles = null;

        if(directory.isDirectory()){//ensured the Pathname is a directory
            directoryFiles = Arrays.asList(
                directory.listFiles(
                    new FileFilter() {
                        @Override
                        public boolean accept(File pathname) {
                            return pathname.isFile();//list only files, skip sub directory
                        }
                    }
                ) );
            }
            return directoryFiles;
    }

    public static void RenameListFiles(List<File> listeFiles){

        listeFiles.stream().forEach(f ->{
            String newFileName = f.getName()
                               .replaceAll("(.*\\.)(sql)", "$1txt");// Change the group 2 (sql) with txt (file extention)
            Path sourceFile = Paths.get(f.getAbsolutePath());// get the file path before Rename
            Path destinationFile = Paths.get(f.getParentFile().getAbsolutePath()+"/"+newFileName);// get the path with the new FileName 
            try{
                Files.move(sourceFile,
                           destinationFile, 
                           StandardCopyOption.REPLACE_EXISTING);// change the old file Name with the destinationFile Name
            } catch(IOException e){
                e.printStackTrace();
            }                
        }
        );
    }

    public static void deleteFileContent(File filePath) throws FileNotFoundException, IOException{
        new RandomAccessFile(filePath, "rw").setLength(0);
    }

    // TXT FILES
    public static boolean checkNoEmptyFiles(File filePath) throws IOException{
        boolean state = true;
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        if(br.readLine() == null){
             state = false;
         }
        br.close();
        return state;
    }

    public static File createNewFile(String newFilePath) throws IOException{
         File f = new File(newFilePath);
         if(!f.exists()) f.createNewFile();
         return f;
    }

    // Read from a txt file AND write to a txt file
    public static void txtFilesProcessing(File txtFileToWriteTo, File txtFileToReadFrom) throws IOException{

        PrintWriter writer = new PrintWriter(txtFileToWriteTo);
        BufferedReader reader = new BufferedReader(new FileReader(txtFileToReadFrom));
        String line;
        //int i = reader.read(); //For reading a singlE character
        while ((line = reader.readLine()) != null) {
            writer.println(line);
        }
        reader.close();
        writer.close();
    }

    // Read from a binary file using Record Size and write to a binary file uisng Record Size
    public static void binaryFilesProcesing(File binaryInputFile) throws IOException{
        int fileRecordSize = 1800; // input File Record Size
        final int BUFFER_SIZE = 1024*fileRecordSize;
        byte[] inputFileBytes = new byte[BUFFER_SIZE]; // read by part according to Record Size (1024 lines at a time)
        FileInputStream inputStream =  new FileInputStream(binaryInputFile);
        int readBytes = 0;
        
        File file = createNewFile("New File Path");
        FileOutputStream outputStream = FileOutputStream(file);

        while ((readBytes = inputStream.read(inputFileBytes)) > 0) {// PROCESSING NEXT 1024 
            for(int i = 0 ; i < readBytes ; i = i + fileRecordSize){
                byte[] record = Arrays.copyOfRange(inputFileBytes, i, i + fileRecordSize);
                outputStream.write(record);// WRITE A LINE FROM THE 1024
            }
        }
        outputStream.close();
        inputStream.close();
    }

    private static FileOutputStream FileOutputStream(File file) {
        return null;
    }

    public static void main(String [] args){

        List<File> files = ListDirectoryFiles("Directory Path");
        RenameListFiles(files);

        List<File> noEmptyFilesListe = files.stream().filter(f -> {
            try {
                return checkNoEmptyFiles(f);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }).collect(Collectors.toList());

        try {

            if(checkNoEmptyFiles(new File("file path"))){ 
                System.out.println("Non empty file");
            }else{ 
                System.out.println("Empty file");
                }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
}
