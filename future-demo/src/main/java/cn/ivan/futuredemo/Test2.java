package cn.ivan.futuredemo;

/**
 * @author yanqi69
 * @date 2021/5/25
 */
public class Test2 {

    public static void main(String[] args) {
        ListNode l1 = build(6);
        ListNode l2 = build(3);
        ListNode listNode = addTwoNumbers(l1, l2);

        while (listNode.next != null){
            System.out.println(listNode.val);
            listNode = listNode.next;
        }
        System.out.println(listNode.val);


    }

    public static ListNode build(int n){
        ListNode l1 = new ListNode(9);
        ListNode temp = l1;
        for(int i =0 ; i< n;i++){
            temp.next = new ListNode(9);
            temp = temp.next;
        }
        return l1;
    }


    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = new ListNode();

        int carry = 0;
        ListNode temp = head;
        while(l1 != null || l2 != null){
            int a = l1 == null ? 0 : l1.val;
            int b = l2 == null ? 0 : l2.val;
            int sum = a + b + carry;
            carry = sum / 10;
            int nodeVal = sum % 10;
            temp.next = new ListNode(nodeVal);
            temp = temp.next;
            l1 = l1 != null ? l1.next:null;
            l2 = l2 != null ? l2.next :null;
        }
        if(carry > 0){
            temp.next = new ListNode(carry);
        }
        return head.next;
    }

}




class ListNode {
    int val;
     ListNode next;
     ListNode() {}
     ListNode(int val) { this.val = val; }ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }
