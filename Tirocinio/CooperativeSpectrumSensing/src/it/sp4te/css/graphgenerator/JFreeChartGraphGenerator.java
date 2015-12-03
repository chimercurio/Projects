package it.sp4te.css.graphgenerator;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class JFreeChartGraphGenerator extends ApplicationFrame implements GraphGenerator{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static int numberOfGraph;
	 public JFreeChartGraphGenerator(String title) {
		 super(title);

	   

	    }
	
	 
	 public void drawGraph(String title, HashMap<String, ArrayList<Double>> detection, int inf, int sup){
		 final XYDataset dataset = createSnrDataset(inf,sup,detection);
	        final JFreeChart chart = createChart(dataset,super.getTitle(),"SNR","% Detection");
	        final ChartPanel chartPanel = new ChartPanel(chart);
	       chartPanel.setPreferredSize(new java.awt.Dimension(650, 500));
	        setContentPane(chartPanel);
	        this.pack();
		     RefineryUtilities.centerFrameOnScreen(this);
		      this.setVisible(true);

			}
	 
	 
	 public  void drawAndSaveGraph(String title,HashMap<String, ArrayList<Double>> detection, int inf, int sup,String path){
		 final XYDataset dataset = createSnrDataset(inf,sup,detection);
	        final JFreeChart chart = createChart(dataset,super.getTitle(),"SNR","% Detection");
	        final ChartPanel chartPanel = new ChartPanel(chart);
	        setContentPane(chartPanel);

			try {


			ChartUtilities.saveChartAsPNG(new File(path), chart, 750, 490);

			} catch (IOException ex) {

			System.out.println(ex.getLocalizedMessage());

			}}
	 
	 
	 public void drawMaliciousUsersToDetectionGraph(String title, HashMap<String, ArrayList<Double>> detection,
				int inf, int sup) throws IOException {
		 final XYDataset dataset = createMSUDataset(detection);
	        final JFreeChart chart = createChart(dataset,super.getTitle(),"% MSU","% Detection");
	        final ChartPanel chartPanel = new ChartPanel(chart);
	       chartPanel.setPreferredSize(new java.awt.Dimension(650, 500));
	        setContentPane(chartPanel);
	        this.pack();
		     RefineryUtilities.centerFrameOnScreen(this);
		      this.setVisible(true);

			}
	 
	 
	 public void drawAndSaveMaliciousUsersToDetectionGraph(String title,
				HashMap<String, ArrayList<Double>> detection, int inf, int sup, String path) throws IOException {
		 final XYDataset dataset = createMSUDataset(detection);
	        final JFreeChart chart = createChart(dataset,super.getTitle(),"% MSU","% Detection");
	        final ChartPanel chartPanel = new ChartPanel(chart);
	       
	        setContentPane(chartPanel);
	        
	        try {


				ChartUtilities.saveChartAsPNG(new File(path), chart, 750, 490);

				} catch (IOException ex) {

				System.out.println(ex.getLocalizedMessage());

				}}

			
	 
	 
	 
	 /**
	     * Creates a sample dataset.
	     * 
	     * @return a sample dataset.
	     */
	    private  XYDataset createSnrDataset(int inf, int sup,HashMap<String, ArrayList<Double>> detection) {
	    	 numberOfGraph=0;
	    	final XYSeriesCollection dataset = new XYSeriesCollection();
	    	for (String graphName : detection.keySet()) {
	    		numberOfGraph++;
	    		final XYSeries series = new XYSeries(graphName);
	    		for(int i=0;i<detection.get(graphName).size();i++){
	    			series.add((double)inf+i, detection.get(graphName).get(i));
	    		
	    		}
	    		
	    		dataset.addSeries(series);
	    	}
	    	
	                
	        return dataset;
	        
	    }
	    
	    
	    private static XYDataset createMSUDataset(HashMap<String, ArrayList<Double>> detection) {
	    	 numberOfGraph=0;
	    	final XYSeriesCollection dataset = new XYSeriesCollection();
	    	for (String graphName : detection.keySet()) {
	    		numberOfGraph++;
	    		final XYSeries series = new XYSeries(graphName);
	    		for(int i=0;i<detection.get(graphName).size();i++){
	    			series.add(i, detection.get(graphName).get(i));
	    		
	    		}
	    		
	    		dataset.addSeries(series);
	    	}
	    	
	                
	        return dataset;
	        
	    }
	    
	    /**
	     * Creates a chart.
	     * 
	     * @param dataset  the data for the chart.
	     * 
	     * @return a chart.
	     */
	    private static JFreeChart createChart(final XYDataset dataset,String title,String xAxis,String yAxis) {
	        
	        // create the chart...
	        final JFreeChart chart = ChartFactory.createXYLineChart(
	            title,      // chart title
	            xAxis,                      // x axis label
	            yAxis,                      // y axis label
	            dataset,                  // data
	            PlotOrientation.VERTICAL,
	            true,                     // include legend
	            true,                     // tooltips
	            false                     // urls
	        );

            XYPlot plot = (XYPlot) chart.getPlot();
	        
			XYLineAndShapeRenderer renderer =  new XYLineAndShapeRenderer(true, true);
	         
			plot.setRenderer(renderer);
		
			ArrayList<Color> colorList=generateColor();
			for(int i=0;i<numberOfGraph;i++){
				plot.getRenderer().setSeriesPaint(i, colorList.get(i));	
				
			}
		
			
			
			
			
	        
			
			renderer.setBaseShapesVisible(true);

			renderer.setBaseShapesFilled(true);
	

			NumberFormat format = NumberFormat.getNumberInstance();

			format.setMaximumFractionDigits(2);
			

			XYItemLabelGenerator generator =

					new StandardXYItemLabelGenerator(

							StandardXYItemLabelGenerator.DEFAULT_ITEM_LABEL_FORMAT,

							format, format);

			renderer.setBaseItemLabelGenerator(generator);

			renderer.setBaseItemLabelsVisible(true);

			return chart;

		}

	   
	    private static ArrayList<Color> generateColor() {
			ArrayList<Color> colorList= new ArrayList<Color>();
			colorList.add(Color.BLUE);
			colorList.add(Color.RED);
			colorList.add(Color.GREEN);
			colorList.add(Color.ORANGE);


			return colorList;
		}


		





		


		
}
