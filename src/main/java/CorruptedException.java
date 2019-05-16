public class CorruptedException extends Exception {
    private String message;

    public CorruptedException(String message){
        this.message = message;
        System.out.println(message);
    }

}
