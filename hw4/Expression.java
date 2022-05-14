//Geunuk Na, Geunuk.na@stonybrook.edu 
package hw214_4;

public class Expression {
    private static enum Action { Push, Pop }
    private static Action[][] act;
    
    static {
        act = new Action[128][128]; //[stack top][input token]
        act['#']['('] = act['(']['('] = act['+']['('] = act['-']['('] = act['*']['('] = act['/']['('] = Action.Push;
                        act['('][')'] = act['+'][')'] = act['-'][')'] = act['*'][')'] = act['/'][')'] = Action.Pop;
        act['#']['+'] = act['(']['+'] =                                                                 Action.Push;
                                        act['+']['+'] = act['-']['+'] = act['*']['+'] = act['/']['+'] = Action.Pop;
        act['#']['-'] = act['(']['-'] =                                                                 Action.Push;
                                        act['+']['-'] = act['-']['-'] = act['*']['-'] = act['/']['-'] = Action.Pop;
        act['#']['*'] = act['(']['*'] = act['+']['*'] = act['-']['*'] =                                 Action.Push;
                                                                        act['*']['*'] = act['/']['*'] = Action.Pop;
        act['#']['/'] = act['(']['/'] = act['+']['/'] = act['-']['/'] =                                 Action.Push;
                                                                        act['*']['/'] = act['/']['/'] = Action.Pop;
        act['#']['$'] = act['(']['$'] = act['+']['$'] = act['-']['$'] = act['*']['$'] = act['/']['$'] = Action.Pop;
    }
    
    public static String infixToPostfix(String expr) {
        //TODO: implement this method
    	Scanner scan = new Scanner(expr);
        Stack<Integer> num = new StackByArray<Integer>(expr.length());
        Stack<Character> oper = new StackByArray<Character>(expr.length());
        String s = "";
        oper.push('#');
        for (String a : scan) {
           if (Scanner.isDigit(a.charAt(0))) {
              num.push(Integer.parseInt(a));
              s = s + " " + a;
           }
           else if (!Scanner.isAlpha(a.charAt(0)) && !Scanner.isWhiteSpace(a.charAt(0))) {
              int r = 0;
              while (oper.top() != '#' && r == 0&&act[oper.top()][a.charAt(0)] == Action.Pop) {
                 if (oper.top() == '(' && a.charAt(0) == ')') {
                    oper.pop();
                    r = r + 1;
                 }
                 else 
                    s = s + " " + oper.pop();
              }
              if (r == 0) {
                 oper.push(a.charAt(0));
              }
           }
        }
         return s;
    }
    
    public static double evalPostfixExpr(String expr) {
        //TODO: implement this method
    	Scanner scan = new Scanner(expr);
        Stack<Double> doubleStack = new StackByArray<Double>(expr.length());
        for (String s : scan) {
           if (Scanner.isDigit(s.charAt(0))) {
              double d = Double.parseDouble(s);
              doubleStack.push(d);
           }
           else if (!Scanner.isAlpha(s.charAt(0)) && !Scanner.isWhiteSpace(s.charAt(0)) && s.charAt(0) != '$') {
               double z = 0;
              double x = doubleStack.pop();
              double y = doubleStack.pop();
              if (s.charAt(0) == '+')
                 z = y + x;
              else if (s.charAt(0) == '-')
                 z = y - x;
              else if (s.charAt(0) == '*')
                 z = y * x;
              else if (s.charAt(0) == '/')
                 z = y / x;
              doubleStack.push(z);
           }
        }
        double result = doubleStack.pop();
        return result;
    }    
    
}
