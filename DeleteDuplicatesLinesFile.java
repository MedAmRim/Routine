import java.io.*;
import java.util.*;

public class DeleteDuplicatesLinesFile {

    public static class ByteArrayWrapper {

        private final byte[] data;

        public ByteArrayWrapper(byte[] data){
            if(data == null)
                throw new NullPointerException();
            
            this.data = data;
        }

        public boolean equals(Object object){
            if(! ( object instanceof ByteArrayWrapper))
                    return false;

            return Arrays.equals(data,((ByteArrayWrapper)object).data);        
        }
        
        public int hashCode(){
            return Arrays.hashCode(data);
        }
    }

    public static void main(String [] args) throws IOException{

        File inputFile = new File("Path");
        int recordSize = 281;
        final int BUFFER_SIZE = 1024*recordSize;
        byte[] inputFileBytes = new byte[BUFFER_SIZE];
        File outputFile = new File(" Path File ");
        boolean exists = outputFile.exists()  ? outputFile.createNewFile() : true;

        if(exists){

            FileInputStream inputStream = new FileInputStream(inputFile);
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            int bytesRead = 0;
            Map<ByteArrayWrapper, byte[]> inputFileMap = new HashMap<ByteArrayWrapper, byte[]>();

            while (( bytesRead = inputStream.read(inputFileBytes)) > 0) {
                for(int i = 0 ; i < bytesRead ; i = i + recordSize){
                    byte[] record = Arrays.copyOfRange(inputFileBytes, i, i + recordSize);
                    ByteArrayWrapper recordPrefix = new ByteArrayWrapper(record);

                    inputFileMap.put(recordPrefix, record);
                }
            }

            inputFileMap.forEach((Key, value) -> {
                try {
                    outputStream.write(value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        }

        

    }
}