public class CardTest {
    public static void main(String[] args) {
      Card c = new Card(5);
      System.out.println(c.getValue()==5);
      System.out.println(c.toString().equals("5"));
      try { new Card(-1); System.out.println("FAIL"); } catch (IllegalArgumentException e) { System.out.println("OK"); }
    }
  }
