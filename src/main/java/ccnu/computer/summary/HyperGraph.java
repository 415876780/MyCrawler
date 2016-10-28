package ccnu.computer.summary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

import Jama.Matrix;

import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;

public class HyperGraph {
	private List<String> hyperedge_list;// �ִ�ǰ�ľ���
	private List<String> tokenizerHyperedge_list;// �ִʺ�ľ���
	private Set<String> vertex_set;
	private List<Map.Entry<String, Double>> sortedEdgesList = null;// ��PageRankScore�����ı�
	private List<Map.Entry<String, Double>> sortedVertexsList = null;// ��PageRankScore�����ĵ�
	List<Integer> reSortList = new ArrayList<Integer>();// ��sinkpoint����������Ԫ��Ϊ�÷��ɸߵ��͵ľ����±�

	private Matrix B;// ��Ȩ�ؾ���n*1
	private Matrix Dv;// DvΪ�ԽǾ���Ԫ��ֵΪ�ڵ�Ķȣ�degree��
	private Matrix De;// DeΪ�ԽǾ���Ԫ��ֵΪ���ߵĶȣ�degree��
	private Matrix We;// WeΪ�ԽǾ���Ԫ��ֵΪԪ��ֵΪ���ߵ�Ȩ��
	private Matrix H;// �ڵ���ߵĹ�ϵ����
	public Matrix Pe;// ����ת�ƾ���
	public Matrix Pv;// ��ת�ƾ���
	public Matrix V;// RandomWalk�㷨��������������ȶ��÷�
	public Matrix U;// RandomWalk�㷨������������ȶ��÷�
	private Matrix E1;// ��λ����m * 1
	private Matrix E2;// ��λ����n * 1
	private double a = 0.85; // damping factor

	/**
	 * ���캯���ʼ����ͼ
	 */
	public HyperGraph(String text) {
		hyperedge_list = new ArrayList<String>();
		tokenizerHyperedge_list = new ArrayList<String>();
		vertex_set = new TreeSet<String>();

		initHyperedge(text);
		calculate_H();
		calculate_De();
	}

	/**
	 * ��ʼ����ϵ����H
	 */
	public void calculate_H() {
		List<String> list = new ArrayList<String>(vertex_set);// �ѵ㼯��תΪlist�����������indexOf������Ԫ���±�
		List<Double> list2 = new ArrayList<Double>(); // ���������H���е�������ֵ��������湹��H����
		for (String s : tokenizerHyperedge_list) {
			double a[] = new double[list.size()];
			String ss[] = s.split(" ");
			for (String sss : ss) {
				if (list.contains(sss)) {
					a[list.indexOf(sss)] = 1;
				}
			}
		    for (int i = 0; i < list.size(); i++) {
				list2.add(a[i]);
			}
		}
		double array_H[] = new double[vertex_set.size()
				* tokenizerHyperedge_list.size()];// ����H�����������鳤��Ϊ����H�����г˻�
		for (int i = 0; i < array_H.length; i++) {
			array_H[i] = list2.get(i);
		}
		H = new Matrix(array_H, vertex_set.size());
		E1 = new Matrix(H.getRowDimension(), 1, 1.0 / H.getRowDimension());
		E2 = new Matrix(H.getColumnDimension(), 1, 1.0 / H.getColumnDimension());
		U = new Matrix(H.getRowDimension(), 1, 1.0 / H.getRowDimension());
		V = new Matrix(H.getColumnDimension(), 1, 1.0 / H.getColumnDimension());
		Dv = new Matrix(H.getRowDimension(), H.getRowDimension());// m*m����
		We = new Matrix(H.getColumnDimension(), H.getColumnDimension());// n*n����
		
		
	}

	/**
	 * DeΪ�ԽǾ���Ԫ��ֵΪ���ߵĶȣ�degree��
	 */
	public void calculate_De() {
		De = new Matrix(H.getColumnDimension(), H.getColumnDimension());// n*n����
		double array_H[][] = H.getArrayCopy();
		double degree;
		for (int n = 0; n < H.getColumnDimension(); n++) {
			degree = 0;
			for (int m = 0; m < H.getRowDimension(); m++) {
				degree += array_H[m][n];
			}
			De.set(n, n, degree);
		}

		if (De.rank() < De.getRowDimension()) { // De����С���У����У���˵��De�жԽ�������0Ԫ�أ���Ѷ���Ϊһ���ϴ�ֵ��1000����������������
			for (int i = 0; i < De.getColumnDimension(); i++) {
				if (De.get(i, i) == 0) {
					De.set(i, i, 1000);
				}
			}
		}
		
	}

	/**
	 * PeΪ��ei��ej��ת�ƾ���
	 */
	public void calculate_Pe() {
		if (Dv.rank() < Dv.getRowDimension()) { 
			for (int i = 0; i < Dv.getColumnDimension(); i++) {
				if (Dv.get(i, i) == 0) {
					Dv.set(i, i, 1000);
				}
			}
		}
		Pe = De.inverse().times(H.transpose()).times(Dv.inverse()).times(H).times(We);// P=De' * HT * Dv' * H * We
		NormalizationPe();
	}

	/**
	 * PvΪvi��vj��ת�ƾ���
	 */
	public void calculate_Pv() {
		if (Dv.rank() < Dv.getRowDimension()) { 
			for (int i = 0; i < Dv.getColumnDimension(); i++) {
				if (Dv.get(i, i) == 0) {
					Dv.set(i, i, 1000);
				}
			}
		}
		Pv = Dv.inverse().times(H).times(We).times(De.inverse()).times(H.transpose());// P=Dv' * H * We * De' * HT
		NormalizationPv();
	}

	/**
	 * Pe��һ��
	 */
	public void NormalizationPe() {
		for (int i = 0; i < Pe.getRowDimension(); i++) {
			Pe.set(i, i, 0);
		}
		double total;
		for (int m = 0; m < Pe.getRowDimension(); m++) {
			total = 0;
			for (int n = 0; n < Pe.getColumnDimension(); n++) {
				total += Pe.get(m, n);
			}
			for (int n = 0; n < Pe.getColumnDimension(); n++) {
				if (total != 0) {
					Pe.set(m, n, Pe.get(m, n) / total);
				}
			}
		}
	}

	/**
	 * Pv��һ��
	 */
	public void NormalizationPv() {
		for (int i = 0; i < Pv.getRowDimension(); i++) {
			Pv.set(i, i, 0);
		}
		double total;
		for (int m = 0; m < Pv.getRowDimension(); m++) {
			total = 0;
			for (int n = 0; n < Pv.getColumnDimension(); n++) {
				total += Pv.get(m, n);
			}
			for (int n = 0; n < Pv.getColumnDimension(); n++) {
				if (total != 0) {
					Pv.set(m, n, Pv.get(m, n) / total);
				}
			}
		}
	}

	/**
	 * ��ʼ����ͼ�ıߺ͵�
	 */
	public void initHyperedge(String text) {
		text = text.replace("��", "");
		List<Term> list;
		StringBuffer buff = new StringBuffer();
		String sentence[] = text.split("[\n������]");
		for (int i = 0; i < sentence.length; i++) {
			if (sentence[i].trim().equals("") || sentence[i].length() < 9)
				continue;
			hyperedge_list.add(sentence[i].trim());
//			System.out.println(sentence[i]);
			list = StandardTokenizer.segment(sentence[i]);
			buff.setLength(0);
			for (Term t : list) {
				if (CoreStopWordDictionary.contains(t.toString()) || t.toString().trim().equals("")) {
					continue;
				}
				vertex_set.add(t.toString());
				buff.append(t.toString() + " ");
			}
			tokenizerHyperedge_list.add(buff.toString().trim());
		}
	}

	public void testIterative() {
		boolean flag1 = false;
		boolean flag2 = false;
		Matrix M1 = U.copy();
		Matrix M2 = V.copy();
		Matrix M3, M4, M5, M6;
		int n = 0;                                   //nͳ�Ƶ�����
		while (!flag1 || !flag2) {
			iterativeReinforce();
			M3 = U.copy();
			M4 = V.copy();
			M5 = M1.minus(M3);
			M6 = M2.minus(M4);
			M1 = M3.copy();
			M2 = M4.copy();
			for (int i = 0; i < M5.getRowDimension(); i++) {
				if (Math.abs(M5.get(i, 0)) > 0.0001) {
					flag1 = false;
					break;
				}
				flag1 = true;
			}

			for (int i = 0; i < M6.getRowDimension(); i++) {
				if (Math.abs(M6.get(i, 0)) > 0.0001) {
					flag2 = false;
					break;
				}
				flag2 = true;
			}
			n++;
		}
	}

	public void iterativeReinforce() {
		Matrix M = De.inverse();
		B = M.times(M).times(H.transpose().times(U).plus(E2));
		Matrix M2 = H.times(B);
		for (int i = 0; i < M2.getRowDimension(); i++) {
			Dv.set(i, i, M2.get(i, 0));
		}
		for (int i = 0; i < B.getRowDimension(); i++) {
			We.set(i, i, B.get(i, 0));
		}
		calculate_Pe();
		V = Pe.transpose().times(V).times(a).plus(E2.times(1 - a));

		B = V;
		Matrix M1 = H.times(B);
		for (int i = 0; i < M1.getRowDimension(); i++) {
			Dv.set(i, i, M1.get(i, 0));
		}
		for (int i = 0; i < B.getRowDimension(); i++) {
			We.set(i, i, B.get(i, 0));
		}
		calculate_Pv();
		U = Pv.transpose().times(U).times(a).plus(E1.times(1 - a));

	}

	public String getSummaryAndKeywords(int n, int m) {
		sortHyperedges();
		sortVertexs();
		StringBuffer buff = new StringBuffer();
/*
		buff.append("<�ؼ��>��");

		for (Map.Entry<String, Double> e : sortedVertexsList) {
			if (e.getKey().length() < 2) {
				continue;
			}
			if (m-- > 0)
				buff.append(e.getKey() + " ");
		}
		buff.append("\n\n<ժҪ>:\n    ");*/
		List<String> list = new ArrayList<String>();
		int sumLength = 0;
		for (Map.Entry<String, Double> e : sortedEdgesList) {// ��������ľ��ӵ��ļ�
			sumLength += e.getKey().length();
			if (sumLength > n) {
				break;
			}
			list.add(e.getKey());
		}
		for (String s : hyperedge_list) {
			if (list.contains(s) && !buff.toString().contains(s))
				buff.append(s + "��");
		}
		
		
		return buff.toString();
	}

	public void sortHyperedges() {
		Map<String, Double> edgeScoreMap = new HashMap<String, Double>();
		for (int i = 0; i < V.getRowDimension(); i++) {
			edgeScoreMap.put(hyperedge_list.get(i), V.get(i, 0));
		}

		sortedEdgesList = new ArrayList<Map.Entry<String, Double>>(
				edgeScoreMap.entrySet());
		// ͨ��Collections.sort(List I,Comparator c)������������
		Collections.sort(sortedEdgesList,
				new Comparator<Map.Entry<String, Double>>() {

					
					public int compare(Entry<String, Double> o1,
							Entry<String, Double> o2) {
						if (o1.getValue() >= o2.getValue()) {
							return -1;
						} else {
							return 1;
						}
					}
				});
	}

	public void sortVertexs() {
		Map<String, Double> vertexsScoreMap = new HashMap<String, Double>();
		Iterator<String> iter = vertex_set.iterator();
		for (int i = 0; i < U.getRowDimension(); i++) {
			vertexsScoreMap.put(iter.next(), U.get(i, 0));
		}

		sortedVertexsList = new ArrayList<Map.Entry<String, Double>>(
				vertexsScoreMap.entrySet());
		// ͨ��Collections.sort(List I,Comparator c)������������
		Collections.sort(sortedVertexsList,
				new Comparator<Map.Entry<String, Double>>() {

					
					public int compare(Entry<String, Double> o1,
							Entry<String, Double> o2) {
						if (o1.getValue() >= o2.getValue()) {
							return -1;
						} else {
							return 1;
						}
					}
				});
	}
	public static String getSummary(String text,int length){
		HyperGraph graph= new HyperGraph(text);
		graph.testIterative();
		String summary=graph.getSummaryAndKeywords(200, 5);
		return summary;
	}
	public static void main(String[] args) {
		String text="����ҵ���о����ԣ�һ����ҵ����׼��������ҵ��Ʒ�ƣ�������ҵ����Ʒ�������ص�һ�ֹ۵���Ϊ�����������Σ���һ������Ǽ۸�������ľ���ڶ��������ר�����ľ�����������Ǳ�׼���ƶȵľ���������ڲ�����ҵ�ѽ����߲�εľ���һ������ҵ����ӿ�֣����ڽ���������б�׼���½��ͿƼ����½��п���������������ڵ��������£�������������� �����������½��������ǶԴ��µĽ���ͷ��֣�Ҳ�ǶԴ��µ��������������һ���������עĿ�ĵط������Ǵ��������Ӷ�Ԫ�����²�θ�ӷḻ����ʾ���ڴ����ͳ��н���ȡ�ó���� ��������ġ�Ӣ�۰��ϣ��������ϴ�缶�Ĳ�ҵ��ͷ��Ҳ�������С��������ҵ��������๫������λ����Ϊ�����˵ȴ���ҵ����������ף��ɽ�������˸���³ɹ��ٶ��ǹ�ʼ��ģ��ڶഴ������С��ҵ���ϰ����˸��ˣ����������Ը��ԵĴ��¡����ǰ񣬲�����Ϊʲô���������г���ʤ��һ�����֮�����д���֮�ǵĹ�ʣ�������Ϊ��������Ĵ�������ҵ�ʹ������˲��ڷ��ⷢ�ȡ� ���������޴����ڣ��봴�������Ԫ����Ӧ���ǣ����ڴ��»����Ⱥ͹�������˺ܴ�Ľ��ڿƼ����·��棬���»���������˵��ӡ������������Ƚ�����ҵ��Ҳ�漰����ҽҩ������Դ���²��ϵ����˲�ҵ�����н��ڡ��������ִ����ҵ�����������һ�ֲ�ҵ��ͿƼ�������������Ӧ��Ҳ���ҹ��������ٽ������ս��ת����������ϵ����ʾ�����ڴ�������Ĳ�������ڱ�׼���·��棬�Ƽ������������׼��ս�����Ͻ��˶���Դ�����2006����ȫ������ʵʩ���б�׼��ս�������������йز���ͨ�������׼�����������׼���½���������׼������ʦ�ƶȵȰ취�������������ҵ��λ����������ת��Ϊ��׼���ƣ�������ǿ����ҵ��������������������������������ʱ�׼�ƶ�������Ȩ������ǿ��ҵ�������ƣ������ʾ��������ӿ콨���ʻ����к͹�����������ͳ��ж�����Ҫ���塣 ������������Ϊ�������ڱ�׼���½��ͿƼ����½��õ������֡�һ�ǶԴ������˲ŵĳ�����ء����»�Ĺؼ�Ҫ�����˲š������£�����Ҫ�ѽ�����ʵ���ˣ����������Ч������οƼ����½��У���ཱ��г��˴�����Ա�����֣���������ʱ���Ӣ�ۡ������������֣���������ᵽÿһ���ش��³ɹ�ı�����һ��������ġ��ʻ�ĸ��ˡ��������ǵļ��Ŭ����ʹ��Щ����������桢�ƶ�����Ĵ��³ɹ�����ʵ��������Ӧ�õ�ȫ�������ء����Ǵ����츣�˺���ᡣ����ܵ����õĴ������ݷḻ����Щ��������ϢϢ��أ��񼲲����ء��߲�������⡢��Ұ��԰�滮�ȣ�������չ�ע��ҽ�Ʊ��ϡ�ʳƷ��ȫ���ȵ��йأ�������ҽ������Σ���ֱ�����������ݽ�����ʽ�ȣ��������ڹ淶�������?��߹�������ˮƽ�����������ĸ�����������Ҫ���á� ���������Ը��Ľ�������ȫ���Ĵ��»�����";
		//String text="12��7�����磬��У���п����̼��Ź����п��ڴ�ѧԺǩԼ��ʽ���人����ͩѧԺ������ʽ���ݷ��ʻ������ľ��С��п����̼�����ϯ�ܲ����̾�߼����ܲ������⡢�人�пƼ��ָ��ֳ���������УУ�����ڿ�����У���̺���У��ί�������졢��У���������Լ����ְ�ܲ��Ÿ����ˡ���ҵ��ʦ��?�����ŶӴ��ȳ�ϯ����������У��У���̺������֡�������´����ᵽ���½������Ҫ�ԣ���ǿ���������Ǵ��´�ҵ���������ڹ������������ҵ���񣬿�չ��ҵ���ѧ��ɳ��ɲš��Ծ��÷�չת�ͣ��ɾ͹�����������Ҫ���塣ͬʱ���人�мӴ󲽷�֧�ִ�ѧ���´�ҵ�����ʦҲע��ѧ��ĸ��Ի�����Ϊ��ѧ��ҵ�ṩ��רҵ����ָ������Ҳ�ᵽ��ѧУ�ڴ�ҵʵ���ϵ��ϵ�������⣬���ʾ����У�������Ӧע�ش�ҵ������ѵ��ָ����������ҵ�γ̡��̲���ϵ���Խ���ҵ����ǿ���Ի���ҵ������ʵʩ��ͬ�Ľ����ƶȣ�������ҵ�ʽ�ȡ����̾����Ȳ�������ҵ����Ӧ������ҵ�͸�У��ϡ������ϵĻر������ɾ͸к�δ���ķ�չ�ռ��ǹ�˾���ǵ����⡣�����й�ĸ�У�Ĵ�ҵ�ʵ����ӣ������͹�ҵĽ���ת�ͣ���ҵ�͸�У�������´�ҵ��������Ҫ����ָ��������Ļ�����Ѿ������˴�ҵ���͸���뻪ʦ�ĺ���������ڴ�ҵʦ�ʵ�������Ӱ�������Զ����Ҳ��������˾�����ԺУ������Ҳ�ﵽԺУ��Эͬ���ˡ����ƻ�����Ŀ�ġ����ڿ������̾�ǩ����ʦ����ѧ�п��ڴ�ѧԺս�Ժ���Э�飬����人�пƼ��ָ��ֳ�����������У����ͩѧԺ�ƣ�������У�����ơ���̸���ڣ���ʦУ�Ѽ洴ҵ��ʦ���̴�������ܾ��?����ָ��������ڴ�ҵ��������·��������ȥ���������죬�淶�����촴ҵ��ѧ��ҵ�����⣬��ҵǰ�������տ���ʱ��Ҫ��ȡ��ʩ����ҵ�߹�ܷ��գ���ַ���ѧУ���п����̼��ź��������ơ��������?���Ӱ����˴λ����Բ������־����У��ѧ���´�ҵ���������µ�̨�ף�������̱������塣";
		String summary=getSummary(text, 200);
		System.out.println(summary);
		/*System.out.println( StandardTokenizer.segment("��ҵ���о����ԣ�һ����ҵ����׼��������ҵ��Ʒ�ƣ�"));*/
		
	}

}
