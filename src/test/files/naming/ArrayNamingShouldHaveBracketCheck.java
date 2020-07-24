
public class ArrayTypeStyleTest1{
    private String[] array1;
    public void f(){
        String[] array2;
    }
}


public class ArrayTypeStyleTest2{
    public void ConfParse(String str){
        TreeMap<String, String> mapConfKeyValue = new TreeMap<>();
        String[] params = str.split("&");
        for (String param : params) {
            String fields[] = param.split("=");     // Noncompliant
            if (fields.length != 2) {
                continue;
            }
            String key = fields[0].trim();
            String value = fields[1].trim();
            mapConfKeyValue.put(key, value);
        }
    }
}

