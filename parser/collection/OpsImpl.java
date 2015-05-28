import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Kunal Chowdhury on 5/27/2015.
 */
public class OpsImpl {
    interface Action{
        //Object[] left();
        Action right();
        Object invoke(Object o);
    }
    static class Idempotent implements Action{
        String field;

        public Idempotent(String field) {
            this.field = field;
        }

        @Override
        public Action right() {
            return null;
        }

        @Override
        public Object invoke(Object o) {
            return field;
        }
    }

    static class Split implements Action {
        Object[] obj ;
        Action right ;
        Method m ;
        public Split(int index, String left, Action right) throws NoSuchMethodException {
            this.obj = new Object[]{left, index};
            this.right = right;
            this.m = String.class.getDeclaredMethod("split", String.class);
        }

        //@Override
        //public Object[] left() {
          //  return obj;
        //}

        @Override
        public Action right() {
            return right;
        }

        @Override
        public Object invoke(Object o) {
            try {
                String[] s = (String[]) m.invoke(o, obj[0]);
                return s[((int) obj[1])];
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return null ;

        }
    }

    static class Concat implements Action {
        Action right;
        Method m ;
        Object[] obj ;
        Concat(String left, Action right) {
            this.obj = new Object[]{left};
            this.right = right;
            try {
                this.m = String.class.getDeclaredMethod("concat", String.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        /*@Override
        public Object[] left() {
            return obj;
        }
        */
        @Override
        public Action right() {
            return right;
        }

        public Object invoke(Object o){
            try {
                return m.invoke(obj[0], o);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    static String traverse(Action root) throws InvocationTargetException,
            IllegalAccessException {
        if(root == null ){
            return "";

        }
        return root.invoke(traverse(root.right())).toString();

    }

    public static void main1(String[] args) throws Exception {
        Method m = String.class.getDeclaredMethod("split", String.class, int.class);
        System.out.println(m.invoke("a,b",",",0 ));
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Concat c1 = new Concat("f",null);
        Concat c2 = new Concat("e,", c1);
        Concat c3 = new Concat("d,", c2);
        Concat c4 = new Concat("c,", c3);
        Concat c5 = new Concat("b,", c4);
        Concat c6 = new Concat("a,", c5);


        System.out.println(traverse(c6));

        try {
            String traverse = traverse(new Split(1, ",", new Idempotent("a,b")));
            System.out.println(traverse);
            System.out.println(traverse(new Split(4, ",", c6)));
            System.out.println(traverse(new Concat(traverse(c1), new Concat(traverse, null))));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        //  Split s1 = new Split(0, )

    }
}
