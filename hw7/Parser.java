package hw214_7;

public class Parser {
    private static class Node {
        public Integer num;
        public Character opr;
        public Node(Integer num) { this.num = num; }
        public Node(Character opr) { this.opr = opr; }
        public String toString() {
            return num != null ? num.toString()
                :  opr != null ? opr.toString()
                :  ""
                ;
        }
    }
    
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
    
    public static BinaryTree<Node> parseExpr(String expr) {
        Scanner scan = new Scanner(expr);
        Stack<LinkedBinaryTree<Node>> tree = new StackByArray<LinkedBinaryTree<Node>>();
        Stack<Character> opr = new StackByArray<Character>();
        
        //TODO: - parseExpr will be similar to evalExpr function that evaluates
        //        infix expressions.
        //      - Here, instead of using the operand stack, we push/pop subtrees of
        //        the parse tree to/from the tree stack.  
        //      - When popping an operator, pop two parse-trees from the tree stack; 
        //        build a parse-tree rooted at the operator; and push the resulting tree
        //        onto the tree stack
    }
    public static double evalExpr(BinaryTree<Node> tree) {
        LinkedBinaryTree<Node> parseTree = (LinkedBinaryTree<Node>) tree;
        Stack<Double> num = new StackByArray<Double>();
        
        //TODO: - evalExpr will be similar to evalPostfixExpr function that evaluates
        //        postfix expressions.
        //      - While traversing the nodes of the parseTree in the post-order,
        //        evaluate the expression by pushing/popping operands to/from the stack num 
    }
    
    public static String infixToPrefix(String expr) {
        String strExp = "";
        LinkedBinaryTree<Node> parseTree = (LinkedBinaryTree<Node>) parseExpr(expr);
        for(Position<Node> p: parseTree.preorder())
            strExp += p.getElement() + " ";
        return strExp;
    }

    public static String infixToPostfix(String expr) {
        String strExp = "";
        LinkedBinaryTree<Node> parseTree = (LinkedBinaryTree<Node>) parseExpr(expr);
        for(Position<Node> p: parseTree.postorder())
            strExp += p.getElement() + " ";
        return strExp;
    }
    
    public static String infixToInfix(String expr) {
        String strExp = "";
        LinkedBinaryTree<Node> parseTree = (LinkedBinaryTree<Node>) parseExpr(expr);
        for(Position<Node> p: parseTree.inorder())
            strExp += p.getElement() + " ";
        return strExp;
    }
}
