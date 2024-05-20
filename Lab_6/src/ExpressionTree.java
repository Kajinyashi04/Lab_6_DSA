import java.util.Stack;

class TreeNode {
    char value;
    TreeNode left;
    TreeNode right;

    TreeNode(char value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }
}

public class ExpressionTree {

    public static TreeNode constructExpressionTree(String expression) {
        Stack<TreeNode> stack = new Stack<>();

        // Read the expression from right to left
        for (int i = expression.length() - 1; i >= 0; i--) {
            char c = expression.charAt(i);

            // If operand, create a node and push onto the stack
            if (isOperand(c)) {
                stack.push(new TreeNode(c));
            } else if (isOperator(c)) {
                // If operator, pop two operands from stack, create a node with operator as value,
                // and set the operands as left and right children
                TreeNode node = new TreeNode(c);
                node.left = stack.pop();
                node.right = stack.pop();
                stack.push(node);
            }
        }
        // The final node on the stack is the root of the expression tree
        return stack.peek();
    }

    public static void infixTraversal(TreeNode root) {
        if (root != null) {
            if (isOperator(root.value)) {
                System.out.print("(");
            }
            infixTraversal(root.left);
            System.out.print(root.value);
            infixTraversal(root.right);
            if (isOperator(root.value)) {
                System.out.print(")");
            }
        }
    }

    private static boolean isOperand(char c) {
        return Character.isLetter(c) || Character.isDigit(c);
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    public static void main(String[] args) {
        // Example prefix notation expressions
        String expression1 = "+ab";
        String expression2 = "*+ab-cd";
        String expression3 = "+-a*bc+de";

        // Construct expression trees
        TreeNode tree1 = constructExpressionTree(expression1);
        TreeNode tree2 = constructExpressionTree(expression2);
        TreeNode tree3 = constructExpressionTree(expression3);

        // Perform infix traversal to print the infix notation
        System.out.print("Infix Expression 1: ");
        infixTraversal(tree1);
        System.out.println();

        System.out.print("Infix Expression 2: ");
        infixTraversal(tree2);
        System.out.println();

        System.out.print("Infix Expression 3: ");
        infixTraversal(tree3);
        System.out.println();
    }
}
