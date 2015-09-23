	/**
	 *
	 */
	/**
	 * @author CHANDRASAIMOHAN
	 *
	 */
	package com.andr.wearable_shop_on_mobile.Parsers;

	import android.content.Context;
	import android.content.res.AssetManager;
	import android.text.TextUtils;

	import com.andr.wearable_shop_on_mobile.DTO.ListData;

	import org.json.JSONArray;
	import org.json.JSONException;
	import org.json.JSONObject;

	import java.io.IOException;
	import java.io.InputStream;
	import java.util.ArrayList;
	import java.util.List;


	public class WearJsonParser {

		private static WearJsonParser instance;
		Context ctx;

		private WearJsonParser(){

		}

		 public static WearJsonParser getInstance(){
			if(instance == null){
				instance = new WearJsonParser();
			}
			return instance;
		}


		   public String getJSON(String path,Context ctx) {
			 this.ctx = ctx;
				String jsonResponse = null;
				AssetManager am = ctx.getAssets();
				try {
					InputStream is = am.open(path);
					int length = is.available();
					byte[] data = new byte[length];
					is.read(data);
					jsonResponse = new String(data);
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				return jsonResponse;

			}


		   public JSONObject  initializeJSON(String jsonString,Context ctx){
				this.ctx = ctx;
				int statusFlag = 0;
			   JSONObject json = null;
				if(!TextUtils.isEmpty(jsonString)){
					//String jsonResponse = Kamal56Application.getInstance().getJsonResponse();
					try {
						 json = new JSONObject(jsonString);
						if(json!=null){

							return json;
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return json;
			}

		public JSONArray getSelectedCategoryJSONArray(String selectedCategory,JSONObject json){
			JSONArray selectedJSONCategoryArray = new JSONArray();
			JSONObject tutorialJSON;
			if(json!=null){
				try{
						if(json.has(selectedCategory)) {
						//it has it, do appropriate processing

						selectedJSONCategoryArray =  json.getJSONArray(selectedCategory);
					}
				}catch (JSONException e) {
					e.printStackTrace();
				}
			}
			return selectedJSONCategoryArray;
		}

	public List<ListData> getParsedListInfo(JSONArray selectedJSONCategoryArray){
		List<ListData> parsedList = new ArrayList<ListData>();
		try{
	 if(selectedJSONCategoryArray!=null && selectedJSONCategoryArray.length()>0){
		 for(int i=0;i<selectedJSONCategoryArray.length();i++){
			 JSONObject temp = selectedJSONCategoryArray.getJSONObject(i);
			 ListData tempList = new ListData();
			 tempList.setItem1(temp.getString("productimage"));
			 tempList.setItem2(temp.getString("productname"));
			 tempList.setItem3(temp.getString("productprice"));

			 parsedList.add(i,tempList);

		 }
	 }
		}
		catch(JSONException e){
			e.printStackTrace();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return parsedList;
	}

		   //https://stackoverflow.com/questions/32278726/how-to-parse-jsonarray-with-in-json-array-in-android-java
	}