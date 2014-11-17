package com.westlakestudent.camera.watermark.location;

public enum POIType {
	
	PLACE("地点", 0), AGENCY("服务,汽车服务", 1), EDUCATION("教育", 2), ENTERTAINMENT("休闲娱乐", 3), FOOD("餐饮", 4), 
	HOTEL("宾馆,地产小区,银行,金融", 5), HOSPITAL("医疗", 6), LIFE("生活服务", 7), OFFICE("公司企业", 8), SHOP("购物", 9), 
	TRAFFIC("交通", 10), TRAVEL("旅游景点", 11), COLLECT("", 12);
		
	private String mName;  
    private int mId;  
      
    private POIType(String name, int id) {  
        mName = name;  
        mId = id;  
    }  
    
    public static String getName(int index) {  
        for (POIType poi : POIType.values())  
            if (poi.getID() == index) 
                return poi.mName;                
          
        return null;  
    }  
    
    public static POIType getType(String typeName) {  
        for (POIType poi : POIType.values()) 
            if (poi.getName().contains(typeName))
                return poi;              
         
        return POIType.PLACE;  
    } 
    
    public String getName() {  
        return mName;  
    }  
    
    public void setName(String name) {  
        mName = name;  
    }  
    
    public int getID() {  
        return mId;  
    }  
    
    public void setID(int id) {  
        mId = id;  
    }
}
