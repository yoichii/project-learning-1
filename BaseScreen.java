import java.awt.*;
import projectlearning1.*;
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
    private JPanel myDataPanel;
    private JLabel myDataLabel;
    private JLabel opponentDataLabel;
    private Color backgroundColor;
    private BottomLabel speechBubbleLabel;
    private int characterLevel = 0;
    protected ImagePanel backgroundPanel;

    public BaseScreen(String title, Rectangle rectangle, int topMode, Player player) {
        // topMode: 0 - title, 1 - username & score
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                initBaseUI(title, rectangle, topMode, player);
            }
        });
    }

    void initBaseUI(String title, Rectangle rectangle, int topMode, Player player) {
        // config
        setTitle(title);
        setBounds((int)(rectangle.getX()), (int)(rectangle.getY()), (int)(rectangle.getWidth()), (int)(rectangle.getHeight()));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // background panel
        backgroundPanel = new ImagePanel(characterLevel);
        backgroundPanel.setLayout(new BorderLayout());

        // top panel
        JPanel topPanel = new JPanel();
        backgroundColor = new Color(204, 187, 163);
        topPanel.setBackground(backgroundColor);
        topPanel.setLayout(new BorderLayout());

        //// title
        JLabel titleLabel = new JLabel();
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        ImageIcon titleimage = new ImageIcon("images/title.png");
        titleLabel.setIcon(titleimage);
        //// add
        topPanel.add(titleLabel, BorderLayout.CENTER);

        if (topMode >= 1) {
            //myDataLabel = new JLabel("<html>"+player.getUsername()+"<br>"+player.getmyPoint()+"<html>");
            myDataLabel = new JLabel(String.valueOf(player.getmyPoint()));
            //myDataLabel.setFont(new Font("ＭＳ 明朝", Font.BOLD, 20));
            Font font = null;
            try {
                font = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/g_brushtappitsu_freeH.ttf")).deriveFont(Font.BOLD, 24);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (font != null)
                myDataLabel.setFont(font);
            myDataLabel.setPreferredSize(new Dimension(80, 120));
            myDataLabel.setHorizontalAlignment(JLabel.CENTER);
            opponentDataLabel = new JLabel();
            opponentDataLabel.setPreferredSize(new Dimension(80, 120));
            //opponentDataLabel.setFont(new Font("ＭＳ 明朝", Font.BOLD, 20));
            if (font != null)
                opponentDataLabel.setFont(font);
            opponentDataLabel.setHorizontalAlignment(JLabel.CENTER);
            // add
            topPanel.add(opponentDataLabel, BorderLayout.EAST);
            topPanel.add(myDataLabel, BorderLayout.WEST);
        }

        if (topMode == 2) {
            //opponentDataLabel.setText("<html>"+player.getOpponentname()+"<br>"+player.getOpponentPoint()+"<html>");
            //opponentDataLabel.setText(String.valueOf(player.getOpponentPoint()));
        }

        // bottom panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 10));
        bottomPanel.setBackground(backgroundColor);
        
        //// icon
        iconLabel = new JLabel();
        iconLabel.setPreferredSize(new Dimension(100, 100));
        iconLabel.addMouseListener(this);
        //// add
        bottomPanel.add(iconLabel);
        bottomPanel.setPreferredSize(new Dimension(600, 120));
        //// speech bubble
        speechBubbleLabel = new BottomLabel();
        //speechBubbleLabel.setIcon(new ImageIcon("images/roll.png"));
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
        characterLevel = (characterLevel + 1) % 5;
        backgroundPanel.changeLevel(characterLevel);
    }


}

class BottomLabel extends JLabel {
    private Image img;
    private String text = "";

    public BottomLabel() {
        this(new ImageIcon("images/roll.png").getImage());
    }

    public BottomLabel(String img) {
        this(new ImageIcon(img).getImage());
    }

    public BottomLabel(Image img) {
        this.img = img;
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null);
        try {
        Font font = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/g_brushtappitsu_freeH.ttf")).deriveFont(Font.BOLD, 18);
        g.setFont(font);
        g.drawString(text, 60, 50);
        } catch (Exception e) {
            e.printStackTrace();
        }
/*
        g.setFont(new Font("ＭＳ 明朝", Font.BOLD, 20));
        g.drawString(text, 60, 60);
        */
    }

    public void setText(String text) {
        this.text = text;
        repaint();
    }

}
