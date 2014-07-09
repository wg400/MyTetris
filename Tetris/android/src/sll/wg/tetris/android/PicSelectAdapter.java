package sll.wg.tetris.android;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.androidquery.AQuery;
import com.wg.mylib.utils.DensityUtil;
import com.wg.mylib.utils.ListUtil;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

public class PicSelectAdapter extends BaseAdapter {
	private Context context ;
	public List<ImageEntity> images ;
	public int selectIndex = -1 ;
	public PicSelectAdapter(Context context) {
		this.context = context ;
	}
	
	@Override
	public int getCount() {
		if (!ListUtil.isEmpty(images)) {
			return images.size() ;
		}
		return 0;
	}

	@Override
	public ImageEntity getItem(int position) {
		if (!ListUtil.isEmpty(images)) {
			return images.get(position) ;
		}
		return null;
	}
	
	public ImageEntity getItem() {
		if (!ListUtil.isEmpty(images) && selectIndex!=-1) {
			return images.get(selectIndex) ;
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		if (view == null) {
			view = LayoutInflater.from(context).inflate(R.layout.picitem, null) ;
		}
		ImageEntity image = getItem(position) ;
		AQuery aQuery = new AQuery(view) ;
//		LayoutParams params = new LayoutParams(DensityUtil.dip2px(context, 200), DensityUtil.dip2px(context, 400)) ;
		LinearLayout layout = (LinearLayout) aQuery.id(R.id.layout).getView() ;
//		layout.setLayoutParams(params);
		layout.setPadding(DensityUtil.dip2px(context, 5), DensityUtil.dip2px(context, 5),
				DensityUtil.dip2px(context, 5), DensityUtil.dip2px(context, 5));
		if (selectIndex == position) {
			aQuery.id(R.id.layout).background(R.drawable.border) ;
		} else {
			aQuery.id(R.id.layout).backgroundColor(android.R.color.transparent) ;
		}
		AssetManager asm = context.getAssets() ;
		try {
			InputStream is=asm.open("data/"+image.StrPath);
			aQuery.id(R.id.image).image(BitmapFactory.decodeStream(is)) ;
		} catch (IOException e) {
			e.printStackTrace();
		}
		aQuery.id(R.id.text).text(image.name);
		return view;
	}
	
}
