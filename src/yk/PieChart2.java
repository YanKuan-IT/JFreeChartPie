package yk;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
/**
 * 突出显示
 *
 */
@WebServlet("/PieChart2")
public class PieChart2 extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DefaultPieDataset data = new DefaultPieDataset();
		
		data.setValue("苹果",100); 
		data.setValue("梨子",200); 
		data.setValue("葡萄",300); 
		data.setValue("香蕉",400); 
		data.setValue("荔枝",500); 
        
		JFreeChart chart = ChartFactory.createPieChart(
        		"水果产量图",  		// 图表标题
		        data, 
		        true, 			// 是否显示图例
		        false, 			// 是否创建工具提示 (tooltip) 
                false  			// 是否生成 URL 链接
		        ); 
        
		PiePlot pieplot = (PiePlot)chart.getPlot();
        pieplot.setExplodePercent("香蕉", 0.5); 

        FileOutputStream fos_jpg = null; 
        long currentTimeMillis = System.currentTimeMillis();
        String filename = currentTimeMillis+".jpeg";
        try { 
        	//将图片保存至Tomcat服务器WebRoot目录下
            fos_jpg = new FileOutputStream(request.getSession().getServletContext().getRealPath("/")+filename);
            ChartUtilities.writeChartAsJPEG(fos_jpg,1,chart,600,500,null); 
        } catch (Exception e) {
        	System.out.println("error");
		} finally { 
            try { 
                fos_jpg.close(); 
            } catch (Exception e) {
            	System.out.println("error2");
            } 
        }
        request.setAttribute("filename", filename);
        
		request.getRequestDispatcher("index2.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
