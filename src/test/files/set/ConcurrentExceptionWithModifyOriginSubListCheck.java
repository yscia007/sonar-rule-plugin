
import java.util.List;

public class Foo {

    private void method() {
        List<String> originList = new ArrayList<String>();
        originList.add("22");
        List<String> subList = originList.subList(0, 1);
    }
}

public class Foo1 {
    private void method(long aLong) {
        List<String> originList = new ArrayList<String>();
        originList.add("22");
        List<String> subList = originList.subList(0, 1);
        originList.add("22");      // Noncompliant
        originList.remove("22");  // Noncompliant
        originList.clear();        // Noncompliant {{这里使用"clear"可能会导致ConcurrentModificationException}}
    }

    private List<List<User>> listSplit(List<User>  dataList ) {
        List allList = new ArrayList();
        //分批处理
        if (null != dataList && dataList.size() > 0) {
            int pointsDataLimit = 1000;//限制条数
            Integer size = dataList.size();
            //判断是否有必要分批
            if (pointsDataLimit < size) {
                int part = size / pointsDataLimit;//分批数
                int start = 0;
                int end = 0;
                for (int i = 0; i < part; i++) {
                    //1000条
                    start = i*pointsDataLimit;
                    end = (i+1)*pointsDataLimit;
                    List listPage = dataList.subList(start, end);
                    System.out.println(listPage);
                    allList.add(listPage);
                }
                if (size - end > 0) {
                    List listPage = dataList.subList(end, size);
                    allList.add(listPage);
                }
                return  allList;
            } else {
                System.out.println(dataList);
                allList.add(dataList);
                return  allList;
            }
        } else {
            return  null;
        }
    }

}