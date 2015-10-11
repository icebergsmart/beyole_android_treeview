package com.beyole.util.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.beyole.util.Node;
import com.beyole.util.TreeHelper;

/**
 * 写成抽象类的目的就是 除了getView方法是不一样的，其他无论传入什么bean，其他方法都是一样的，没有必要复写
 * 
 * @author Iceberg
 * 
 * @param <T>
 */
public abstract class TreeListViewAdapter<T> extends BaseAdapter {

	protected Context mContext;
	protected List<Node> mAllNodes;
	protected List<Node> mAllVisibleNodes;
	protected LayoutInflater mInflater;
	protected ListView mTree;

	/**
	 * 设置node的点击回调
	 * 
	 * @author Iceberg
	 * 
	 */
	public interface OnTreeNodeClickListener {
		void onClick(Node node, int position);
	}

	private OnTreeNodeClickListener mListener;

	public void setTreeNodeOnclickListener(OnTreeNodeClickListener Listener) {
		this.mListener = Listener;
	}

	public TreeListViewAdapter(ListView tree, Context context, List<T> datas,
			int defaultExpandLevel) throws IllegalArgumentException,
			IllegalAccessException {
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
		mAllNodes = TreeHelper.getSortedNodes(datas, defaultExpandLevel);
		Log.e("datas默认的size",datas.size()+"");
		Log.e("mAllNodes默认的size",mAllNodes.size()+"");
		mAllVisibleNodes = TreeHelper.filterVisibleNodes(mAllNodes);
		Log.e("mAllVisibleNodes默认的size",mAllVisibleNodes.size()+"");
		
		mTree = tree;
		mTree.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				expandOrCollapse(position);
				if (mListener != null) {
					mListener.onClick(mAllVisibleNodes.get(position), position);
				}
			}

		});
	}

	/**
	 * 点击收缩或者展开
	 * 
	 * @param position
	 */
	protected void expandOrCollapse(int position) {
		Node n = mAllVisibleNodes.get(position);
		if (n != null) {
			if (n.isLeaf())
				return;
			n.setExpand(!n.isExpand());
			mAllVisibleNodes = TreeHelper.filterVisibleNodes(mAllNodes);
			Log.e("mAllVisibleNodes的size为", mAllVisibleNodes.size()+"");
			notifyDataSetChanged();
		}
	}

	@Override
	public int getCount() {
		return mAllVisibleNodes.size();
	}

	@Override
	public Object getItem(int position) {
		return mAllVisibleNodes.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Node node = mAllVisibleNodes.get(position);
		Log.e("setview时的mAllVisibleNodes的size",""+mAllVisibleNodes.size());
		convertView = getConvertView(node, position, convertView, parent);
		// 设置padding值
		convertView.setPadding(node.getLevel() * 30, 3, 3, 3);
		return convertView;
	}

	public abstract View getConvertView(Node node, int position,
			View convertView, ViewGroup parent);

}
