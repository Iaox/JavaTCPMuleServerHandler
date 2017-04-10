import java.util.ArrayList;
import java.util.List;

public class MuleList{
	
	private ArrayList<Mule> list;
	
	public MuleList(){
		list = new ArrayList<Mule>();
	}
	
	public boolean add(Mule mule){
		if(!containsName(mule.getName())&& list.add(mule)){
			return true;
		}
		return false;
	}
	public boolean remove(Mule mule){
		if(list.contains(mule)){
			if(list.remove(mule)){
				return true;
			}
		}
		return false;
	}
	public Mule getAvailableMule(){
		for(Mule mule : list){
			if(mule.isFree()){
				return mule;
			}
		}
		return null;
	}
	
	public void printNames(){
		list.forEach(mule -> {
			System.out.println(mule.getName());
		});
	}
	public ArrayList<String> getNames(){
		ArrayList<String> tempList = new ArrayList<String>();
		list.forEach(mule -> {
			tempList.add(mule.getName());
		});
		return tempList;
	}
	
	public boolean containsName(String name){
		return getNames().contains(name);
	}
	
	public void clearMules(){
		list.forEach(mule -> {
			if(mule.getSlave() != null){
				if(mule.getSlave().getSocket().isClosed()){
					System.out.println("lets remove old slave from mule:" + mule.getName());
					mule.removeSlave();
				}
			}
		});
	}
}
