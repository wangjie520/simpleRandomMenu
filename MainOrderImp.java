package com.lunch.simple;
/**
 * ��δ����ȥ��xx
 * ��Ȩ���
 * gpneednum[i]>resultSet.getRow(),people=0������쳣
 */
import java.sql.SQLException;
//import java.util.Scanner;

public class MainOrderImp {

	/**
	 * @param args
	 */
	public static int storeId;
	public static int people;
	public static float maxTotalPrice;
	//
	public static float minTotalPrice;
	public static float actuallyPrice;
	public static int []gpneednum={0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	//���ÿ��groupҪ��Ĳ�������gpneednum[0]:group_id��1�������ɫ�˵�����
	public static int rsid=0;//ResultId[]�±� 
	
	public static void main(String[] args) throws SQLException {
		MainOrderImp mo=new MainOrderImp();
		mo.inputParam();
		//mo.outputParam();
	/*	System.out.println("ȷ�ϣ�Y/N");
		Scanner sc = new Scanner(System.in);
		String check=sc.next();
		if (check.equals("N")){		
		}*/
		int FoodNum=0;
		for(int k=0;k<14;k++){
			System.out.println(gpneednum[k]);
			FoodNum=FoodNum+gpneednum[k];
		}
		
		int []ResultId;
		ResultId=new int[FoodNum];
		mo.randomMenu(ResultId);
		
	
		//�ܼ۸��������
		
		actuallyPrice=mo.caculateprice(ResultId);
		if (storeId==1){
			actuallyPrice=actuallyPrice+1*people;
		}
		System.out.println("actuallyPrice:"+actuallyPrice);
		while(actuallyPrice>maxTotalPrice){
			rsid=0;
			mo.randomMenu(ResultId);
			actuallyPrice=mo.caculateprice(ResultId);
			if (storeId==1){
				actuallyPrice=actuallyPrice+2*people;
			}
		}
		//������
		String resultIds="(";
		for(int i=0;i<ResultId.length-1;i++){
			resultIds=resultIds+ResultId[i]+",";
		}
		resultIds=resultIds+ResultId[ResultId.length-1]+")";
		System.out.println("***Ϊ��ѡ��˵�����******\n");
		SqlQuery sqlQ=new SqlQuery();
		sqlQ.displayResult(resultIds);
		System.out.println("总价"+actuallyPrice);
		System.out.println("人均:"+actuallyPrice/people);

	}
	public void inputParam(){
		/*System.out.println("�������Ķ�Է���");
		 * 
		System.out.println("1.����Ѽ\n");
		Scanner sc = new Scanner(System.in);
		storeId=sc.nextInt();
		System.out.println("�Է�������?\n");
		people=sc.nextInt();
		System.out.println("��ɫ�˼���?\n");
		gpneednum[0]=sc.nextInt();
		System.out.println("���˼���?");
		gpneednum[1]=sc.nextInt();
		System.out.println("��缸��?");
		gpneednum[2]=sc.nextInt();
		System.out.println("�ҳ��缸��?");
		gpneednum[3]=sc.nextInt();
		System.out.println("�����缸��?");
		gpneednum[4]=sc.nextInt();
		System.out.println("���˻缸��?");
		gpneednum[5]=sc.nextInt();
		System.out.println("�ɹ��?");
		gpneednum[6]=sc.nextInt();
		System.out.println("��弸��?");
		gpneednum[7]=sc.nextInt();
		System.out.println("�հ׼���?");
		gpneednum[8]=sc.nextInt();
		System.out.println("�㼸��?");
		gpneednum[9]=sc.nextInt();
		System.out.println("��ʱ�߼���?");
		gpneednum[10]=sc.nextInt();
		System.out.println("ɰ���?");
		gpneednum[11]=sc.nextInt();
		System.out.println("������?");
		gpneednum[12]=sc.nextInt();
		System.out.println("С�Լ���?");
		gpneednum[13]=sc.nextInt();
		
		System.out.println("��໨��?");
		maxTotalPrice=sc.nextFloat();
		sc.close();
		���ٵ���*/
		 storeId=1;
		people=5;
		/*for(int kk=0;kk<14;kk++){
			gpneednum[kk]=0;
		}*/
		int k=(int)(Math.random()*4);
		switch(k){
		case 0:
		gpneednum[0]=1;//��ɫ��һ��
		break;
		case 1:
		gpneednum[1]=1;//����
		break;
		case 2:
		gpneednum[2]=1;//���
		break;
		case 3:
		gpneednum[9]=1;//��
		break;		
		}
		
		k=(int)(Math.random()*5);
		switch(k){
		case 0:
		gpneednum[6]=1;//�ɹ�
		break;
		case 1:
		gpneednum[7]=1;//���
		break;
		case 2:
		gpneednum[8]=1;//�հ�
		break;
		case 3:
		gpneednum[11]=1;//ɰ��
		break;
		case 4:
		gpneednum[12]=1;//汤
		break;
		}
		
		//gpneednum[12]=1;
		k=(int)(Math.random()*3);
		switch(k){
		case 0:
		gpneednum[3]=1;//�ҳ���
		break;
		case 1:
		gpneednum[4]=1;//������
		break;
		case 2:
		gpneednum[5]=1;//���˻�
		break;
		}
		
		gpneednum[10]=1;//���ز�
		
		maxTotalPrice=120;
		
	}
	/*public void outputParam(){
		System.out.println("�Է�������?"+people);
		System.out.println("�ҳ��˼���?"+jiachangcai);
		System.out.println("�ɹ�/��弸��?"+ganguo);
		System.out.println("����/��˼���?"+tangguo);
		System.out.println("��˼���?"+coldfood);
		System.out.println("���ļ���?"+dianxin);
	}*/
	//�������ĳ�����needNum���ˣ������˵�menuid����ResultId[];
	public int isInArr(int temp,int []Arr){
		int duplicate=0;
		for(int s=0;s<=rsid;s++){
			if (temp==Arr[s]){
				duplicate=1;
			}
		}
			return duplicate;
	}
	public void randomGroupMenu(int group_id,int needNum,int []ResultId) throws SQLException{
		SqlQuery sqlQ=new SqlQuery();
		int duplicate;
		int []a=sqlQ.queryResultByGroup(storeId,group_id);
		int groupTotalNum=a.length;//ĳ��˵�����
		for(int j=0;needNum>j;j++){
			int menuid=(int)(Math.random()*groupTotalNum);
			//(int)Math.random()*jiachangcaitotalnumע�����ȼ�����
			//���ȥ��
			duplicate=isInArr(a[menuid],ResultId);
				while(duplicate==1){
					menuid=(int)(Math.random()*groupTotalNum);
					duplicate=isInArr(a[menuid],ResultId);
					//���menuid����ʱ����a���±֮꣬ǰд��duplicate=isInArr(menuid,ResultId)����ȥ��ʧ��;
				}
			duplicate=0;
			ResultId[rsid]=a[menuid];
			rsid=rsid+1;
		}
	}
	public void randomMenu(int []ResultId) throws SQLException{
		for(int gpid=1;gpid<=14;gpid++){
			//���Ч�ʣ�������鲻��
			if(gpneednum[gpid-1]!=0){
			randomGroupMenu(gpid,gpneednum[gpid-1],ResultId);
			}
		}
		
		
	}
	public float caculateprice(int []ResultId) throws SQLException{
		//��resultId[]ת��Ϊ(a[1],a[2]...)
		String result="(";
		for(int i=0;i<ResultId.length-1;i++){
			result=result+ResultId[i]+",";
		}
		result=result+ResultId[ResultId.length-1]+")";
		
		SqlQuery sqlQ=new SqlQuery();
		float totalprice=sqlQ.queryTotalPrice(result);
		return totalprice;
	}
}
