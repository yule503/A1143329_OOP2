import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CorrectLogin extends JFrame {

    public CorrectLogin() {
        // 1. 設定視窗基本屬性
        setTitle("登入");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 關閉視窗時結束程式
        setLocationRelativeTo(null); // 讓視窗顯示在螢幕中央

        // 2. 使用版面配置管理器 (Layout Manager)，避免元件看不到
        setLayout(new GridLayout(3, 2, 10, 10));

        // 3. 建立 UI 元件 (密碼欄位改用 JPasswordField)
        JLabel l1 = new JLabel("帳號:", SwingConstants.CENTER);
        JTextField t1 = new JTextField();

        JLabel l2 = new JLabel("密碼:", SwingConstants.CENTER);
        JPasswordField t2 = new JPasswordField(); // 隱藏密碼輸入內容

        JButton btn = new JButton("登入");
        JLabel resultLabel = new JLabel("", SwingConstants.CENTER); // 用於顯示結果

        // 4. 將元件加入視窗
        add(l1);
        add(t1);
        add(l2);
        add(t2);
        add(btn);
        add(resultLabel);

        // 5. 設定按鈕事件監聽器 (修復字串比較的問題)
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = t1.getText();
                String password = new String(t2.getPassword()); // 取得密碼字串

                // 正確使用 .equals() 進行字串比較，而非 ==
                if ("admin".equals(username) && "1234".equals(password)) {
                    System.out.println("登入成功");
                    resultLabel.setText("登入成功！");
                    resultLabel.setForeground(Color.GREEN);
                } else {
                    System.out.println("帳號或密碼錯誤");
                    resultLabel.setText("帳號或密碼錯誤");
                    resultLabel.setForeground(Color.RED);
                }
            }
        });

        // 6. 最後才顯示視窗，確保所有元件已載入
        setVisible(true);
    }

    public static void main(String[] args) {
        // Swing 建議在 Event Dispatch Thread (EDT) 中建立 UI
        SwingUtilities.invokeLater(() -> new CorrectLogin());
    }
}