import java.io.*;
import java.nio.file.*;
import java.util.*;

public class RenameFiles{

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



    public static void main(String [] args){

        List<File> files = ListDirectoryFiles("Directory Path");
        RenameListFiles(files);

    }
    
}
