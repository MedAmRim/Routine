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

    public static boolean checkNoEmptyFiles(File filePath) throws IOException{
        boolean state = true;
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        if(br.readLine() == null){
             state = false;
         }
        br.close();
        return state;
    }

    public static File createNewFile(String newFilePath){
        return new File(newFilePath);
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
