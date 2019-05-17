import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public abstract class BaseScreen extends JFrame implements MouseListener {
	private static final long serialVersionUID = 1L;

    private String text;
    private ImageIcon icon;
    private JLabel iconLabel;
    private Color backgroundColor;
    private JLabel speechBubbleLabel;
    protected ImagePanel backgroundPanel;
    private String[] imageURL = {"images/level1.png", "images/level2.png", "images/level3.png", "images/level4.png", "images/level5.png"};
    private int urlIndex = 0;


    public BaseScreen(String title) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                initBaseUI(title);
            }
        });
    }

    void initBaseUI(String title) {
        // config
        setTitle(title);
        setBounds(100, 100, 600, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // background panel
        backgroundPanel = new ImagePanel();
        backgroundPanel.setLayout(new BorderLayout());

        // top panel
        JPanel topPanel = new JPanel();
        backgroundColor = new Color(204, 187, 163);
        topPanel.setBackground(backgroundColor);
        //// title
        JLabel titleLabel = new JLabel();
        ImageIcon titleimage = new ImageIcon("images/title.png");
        titleLabel.setIcon(titleimage);
        //// add
        topPanel.add(titleLabel);

        // bottom panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 10));
        bottomPanel.setBackground(backgroundColor);
        //// icon
        iconLabel = new JLabel();
        icon = new ImageIcon(imageURL[urlIndex]);
        iconLabel.setIcon(icon);
        iconLabel.addMouseListener(this);

        //// add
        bottomPanel.add(iconLabel);
        bottomPanel.setPreferredSize(new Dimension(600, 120));
        //// speech bubble
        speechBubbleLabel = new JLabel();
        speechBubbleLabel.setIcon(new ImageIcon("images/roll.png"));
        speechBubbleLabel.setOpaque(false);
        speechBubbleLabel.setBackground(Color.white);
        speechBubbleLabel.setPreferredSize(new Dimension(400, 120));
        //// add
        bottomPanel.add(speechBubbleLabel);

        // transparent
        topPanel.setOpaque(false);
        bottomPanel.setOpaque(false);

        // add
        backgroundPanel.add(topPanel, BorderLayout.NORTH);
        backgroundPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(backgroundPanel);

    }

    public void setText(String text) {
        this.text = text;

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                speechBubbleLabel.setText(text);
            }
        });
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public Color getBackgroundColor() {
        return this.backgroundColor;
    }

    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
    }

    public void showError(Message message) {
        switch(message.getStatus()) {
            case invalidUsername:
                setText("ユーザ名が間違ってるよ");
                break;
            case invalidPassword:
                setText("パスワードが間違ってるよ");
                break;
            case unknownError:
                setText("エラーが起きたよ\nもう一度試してね");
                break;
            case classNotFoundException:
                setText("サーバから受信できなかったよ\nもう一度試してね");
                break;
            case ioException:
                setText("サーバと通信ができなかったよ\nもう一度試してね");
                break;
            case nullObject:
               setText("サーバと接続ができてないよ\nもう一度試してね");
                break;
            default:
                setText("エラーが起きたよ\nもう一度試してね");
        }
    }

	public static Clip createClip(File path) {
		//指定されたURLのオーディオ入力ストリームを取得
		try (AudioInputStream ais = AudioSystem.getAudioInputStream(path)){
			
			//ファイルの形式取得
			AudioFormat af = ais.getFormat();
			
			//単一のオーディオ形式を含む指定した情報からデータラインの情報オブジェクトを構築
			DataLine.Info dataLine = new DataLine.Info(Clip.class,af);
			
			//指定された Line.Info オブジェクトの記述に一致するラインを取得
			Clip c = (Clip)AudioSystem.getLine(dataLine);
			
			//再生準備完了
			c.open(ais);
			
			
			return c;
		
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		
		} catch (UnsupportedAudioFileException e) {
		
			e.printStackTrace();
		
		} catch (IOException e) {
		
			e.printStackTrace();
		
		} catch (LineUnavailableException e) {
		
			e.printStackTrace();
		
		}
		
		return null;
	
	}


    public void mouseEntered(MouseEvent e){
    }

    public void mouseExited(MouseEvent e){
    }

    public void mousePressed(MouseEvent e){
    }

    public void mouseReleased(MouseEvent e){
    }

    public void mouseClicked(MouseEvent e){
        urlIndex = (urlIndex + 1) % 5;
        icon = new ImageIcon(imageURL[urlIndex]);
        iconLabel.setIcon(icon);
    }

    private void msgChange(String msg){
    }

}
