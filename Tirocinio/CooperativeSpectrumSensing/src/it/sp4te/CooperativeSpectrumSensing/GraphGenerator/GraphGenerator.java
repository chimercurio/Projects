package it.sp4te.CooperativeSpectrumSensing.GraphGenerator;


import static com.googlecode.charts4j.Color.*;


import java.awt.BorderLayout;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;



import com.googlecode.charts4j.AxisLabels;
import com.googlecode.charts4j.AxisLabelsFactory;
import com.googlecode.charts4j.AxisStyle;
import com.googlecode.charts4j.AxisTextAlignment;
import com.googlecode.charts4j.Color;
import com.googlecode.charts4j.Data;
import com.googlecode.charts4j.Fills;
import com.googlecode.charts4j.GCharts;
import com.googlecode.charts4j.Line;
import com.googlecode.charts4j.LineChart;
import com.googlecode.charts4j.LineStyle;
import com.googlecode.charts4j.LinearGradientFill;
import com.googlecode.charts4j.Plots;
import com.googlecode.charts4j.Shape;

public class GraphGenerator {

//Il metodo prende in input una mappa nomeDetection->detection	
	public static void drawGraph(HashMap<String,ArrayList<Double>> detection ) throws IOException{

		ArrayList<Line> lines= new ArrayList<Line>();
		for (String graphName : detection.keySet()) {
		    Line line = Plots.newLine(Data.newData(detection.get(graphName)), Color.newColor("CA3D05"), graphName);
	        line.setLineStyle(LineStyle.newLineStyle(3, 1, 0));
	        line.addShapeMarkers(Shape.DIAMOND, Color.newColor("CA3D05"), 12);
	        //Il colore va modificato ogni volta. Serve un metodo
	        line.addShapeMarkers(Shape.DIAMOND, Color.WHITE, 8);
	        lines.add(line);
	        }
	        
	        //Definisco il chart  
	        LineChart chart = GCharts.newLineChart(lines);
	        chart.setSize(600, 450);
	        chart.setTitle("Detection Methods", WHITE, 14);
	        
	        //Definisco lo stile
	        AxisStyle axisStyle = AxisStyle.newAxisStyle(WHITE, 12, AxisTextAlignment.CENTER);
	      
	        //Etichetta asse y(% di detection)
	        AxisLabels yAxis = AxisLabelsFactory.newNumericRangeAxisLabels(0, 100);
	        yAxis.setAxisStyle(axisStyle);
	      
	        //Etichetta asse x(SNR in DB)
	        AxisLabels xAxis1 = AxisLabelsFactory.newNumericRangeAxisLabels(-30, 5);
	        xAxis1.setAxisStyle(axisStyle);
	        
	        //Etichetta asse x
	        AxisLabels xAxis3 = AxisLabelsFactory.newAxisLabels("SNR (Decibel)", 50.0);
	        xAxis3.setAxisStyle(AxisStyle.newAxisStyle(WHITE, 14, AxisTextAlignment.CENTER));
	        
	        //Etichetta asse y
	        AxisLabels yAxis3 = AxisLabelsFactory.newAxisLabels("% of Detection", 50.0);
	        yAxis3.setAxisStyle(AxisStyle.newAxisStyle(WHITE, 14, AxisTextAlignment.CENTER));
	        
	        
	        //Aggiungo al chart
	        chart.addXAxisLabels(xAxis1);
	        chart.addYAxisLabels(yAxis);
	        chart.addXAxisLabels(xAxis3);
	        chart.addYAxisLabels(yAxis3);
	     
	        //Parametri generali su aspetto
	        chart.setBackgroundFill(Fills.newSolidFill(Color.newColor("1F1D1D")));
	        chart.setAreaFill(Fills.newSolidFill(Color.newColor("708090")));
	        LinearGradientFill fill = Fills.newLinearGradientFill(0, Color.newColor("363433"), 100);
	        fill.addColorAndOffset(Color.newColor("2E2B2A"), 0);
	        chart.setAreaFill(fill);
	        
	        //Mostro il grafico tramite java swing
	        displayUrlString(chart.toURLString());
	    }
	
	//Metodo per rappresentare il grafico in una finestra java swing
	private static void displayUrlString(final String urlString) throws IOException{
        JFrame frame = new JFrame();
        JLabel label = new JLabel(new ImageIcon(ImageIO.read(new URL(urlString))));
        frame.getContentPane().add(label, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
         
	}
	
	
}
