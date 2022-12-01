import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

// Some code originated from
// https://github.com/Madonahs/Graphics-2D-in-Java/blob/master/SimpleGraph/SampleGraph/src/com/madonasyombua/exe/Graph.java
public class GraphCreator extends JPanel{



    private static final long serialVersionUID = 1L;
    private int labelPadding = 60;
    /**change the line color to the best you want;*/
    private Color lineColor = new Color(255,255,254);
    private Color pointColor = new Color(255,0,255 );
    private Color gridColor = new Color(200, 200, 200, 200);
    private static int pointWidth = 10;
    private int numberYDivisions = 6;
    private ArrayList<String> dates = new ArrayList<>();
    private ArrayList<String> numbers = new ArrayList<>();
    private int padding = 20;
	private String YAxisLabel, units;


    public GraphCreator(ArrayList<String[]> tempData, int days, String YLabel, String Units, String title) {
        //
		YAxisLabel = YLabel;
		units = Units;

        ArrayList<String[]> data = new ArrayList<String[]>();
       Collections.reverse(tempData);
		if (days < tempData.size()) {
		    for (int i = 0; i < days; i ++) {
		        data.add(0, tempData.get(i));
            }
        } else {
		    Collections.reverse(tempData);
		    data = tempData;
        }
        System.out.println(data.get(0)[0]);

        /* Main panel */
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(i).length; j++) {
                System.out.println("test: " + i+ " " + j + " " +data.get(i)[j]);
            }
        }
        if (data.size() == 0) {
            System.out.println("No data to read");
            return;
        }
        GraphCreator mainPanel = this;
        mainPanel.setPreferredSize(new Dimension(1000, 600));
        /* creating the frame */
        JFrame frame = new JFrame(title);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        for (int i = 0; i < data.size(); i++) {
            this.dates.add(data.get(i)[0]);
            this.numbers.add(data.get(i)[1]);
        }
        for (int i = 0; i < data.size(); i++) {
            System.out.println("Date: " + data.get(i)[0] + "; Number: "+ data.get(i)[1]);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (getMaxScore() - getMinScore());

        List<Point> graphPoints = new ArrayList<>();
        for (int i = 0; i < numbers.size(); i++) {
            int x1 = (i * (getWidth() - padding * 2 - labelPadding) / (dates.size() - 1) + padding + labelPadding);

            try {
                int y1 = (int) ((getMaxScore() - Double.valueOf(numbers.get(i))) * yScale + padding);
                graphPoints.add(new Point(x1, y1));
            } catch (NumberFormatException e) {
                //Do nothing, the value of y1 just remains as 0
            }


        }

        g2.setColor(Color.WHITE);
        //fill the rect
        g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) -
                labelPadding, getHeight() - 2 * padding - labelPadding);

        drawGridDivisions(g2);

        //Plot points
        g2.setColor(pointColor);
        for (int i = 0; i < graphPoints.size(); i++) {
            int x = graphPoints.get(i).x - pointWidth / 2;
            int y = graphPoints.get(i).y - pointWidth / 2;
            int ovalW = pointWidth;
            int ovalH = pointWidth;
            g2.fillOval(x, y, ovalW, ovalH);
        }

        connectTheDots(g2,graphPoints);

    }

    private void drawGridDivisions(Graphics2D g) {
        g.setColor(Color.BLUE);

        for (int i = 0; i < numberYDivisions + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight() - ((i * (getHeight() - padding * 2 -
                    labelPadding)) / numberYDivisions + padding + labelPadding);
            int y1 = y0;
            if (numbers.size() > 0) {
                g.setColor(gridColor);
                g.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
                g.setColor(Color.BLACK);
                Double yLabelDouble = (double) Math.round(((((getMinScore() + (getMaxScore() - getMinScore()) *
                        ((i * 1.0) / numberYDivisions)) * 100)) / 100) * 100) / 100;
                String yLabel = (yLabelDouble + "");
                System.out.println(yLabel + " " + getMinScore() + " " + (getMaxScore() - getMinScore()) + " " +
                        ((i * 1.0) / numberYDivisions));
                FontMetrics metrics = g.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g.drawString(yLabel, x0 - labelWidth - 6, y0 + (metrics.getHeight() / 2) - 3);

            }
            g.drawLine(x0, y0, x1, y1);
        }
        g.drawString(YAxisLabel,10, 300);
        g.drawString(units, 20, 320);

        for (int i = 0; i < dates.size(); i++) {
            if (dates.size() > 1) {
                int x0 = i * (getWidth() - padding * 2 - labelPadding) / (dates.size() - 1) + padding + labelPadding;
                int y0 = getHeight() - padding - labelPadding;
                int y1 = y0 - pointWidth;
                    g.setColor(gridColor);
                    g.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x0, padding);
                    g.setColor(Color.BLACK);
                    String xLabel = dates.get(i);
                    FontMetrics metrics = g.getFontMetrics();
                    int labelWidth = metrics.stringWidth(xLabel);
                    g.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);

                g.drawLine(x0, y0, x0, y1);
            }
        }
        g.drawString("Date",getWidth()/2, getHeight()-padding);

        // Draw thick perpendicular lines from origin
        g.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
        g.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() -
                padding, getHeight() - padding - labelPadding);
    }

    private void connectTheDots(Graphics2D g, List<Point> graphPoints) {
        for (int i = 1; i < graphPoints.size(); i++) {
            int x0 = graphPoints.get(i-1).x;
            int y0 = graphPoints.get(i-1).y;
            int x1 = graphPoints.get(i).x;
            int y1 = graphPoints.get(i).y;
            g.drawLine(x0, y0, x1, y1);
        }
    }
    /*
     *  getting the min score using Math();
     * getMinScore is an accessor method
     * @Return the minScore
     */


    private double getMinScore() {
        double minScore = Double.MAX_VALUE;
        double score;
        for (String number : numbers) {
            try {
                score = Double.valueOf(number);
            } catch (NumberFormatException e) {
                score = 0;
            }
            minScore = Math.min(minScore, score);
        }
        return minScore;
    }
    /*
     *  getting the max score using Math();
     * getMaxScore is an accessor method
     * @Return the maxScore;
     */

    private double getMaxScore() {
        double maxScore = Double.MIN_VALUE;
        double score;
        for (String number : numbers) {
            try {
                score = Double.valueOf(number);
            } catch (NumberFormatException e) {
                score = 0;
            }
            maxScore = Math.max(maxScore, score);
        }
        return maxScore;
    }

}