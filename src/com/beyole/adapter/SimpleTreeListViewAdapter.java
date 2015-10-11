package com.beyole.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.beyole.treeview.R;
import com.beyole.util.Node;
import com.beyole.util.TreeHelper;
import com.beyole.util.adapter.TreeListViewAdapter;

public class SimpleTreeListViewAdapter<T> extends TreeListViewAdapter<T> {

	public SimpleTreeListViewAdapter(ListView tree, Context context,
			List<T> datas, int defaultExpandLevel)
			throws IllegalArgumentException, IllegalAccessException {
		super(tree, context, datas, defaultExpandLevel);
	}

	@Override
	public View getConvertView(Node node, int position, View convertView,
			ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item, parent, false);
			holder = new ViewHolder();
			holder.mImageView = (ImageView) convertView
					.findViewById(R.id.id_item_icon);
			holder.mTextView = (TextView) convertView
					.findViewById(R.id.id_item_text);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (node.getIcon() == -1) {
			holder.mImageView.setVisibility(View.INVISIBLE);
		} else {
			holder.mImageView.setVisibility(View.VISIBLE);
			holder.mImageView.setImageResource(node.getIcon());
		}
		holder.mTextView.setText(node.getName());
		return convertView;
	}

	private class ViewHolder {
		ImageView mImageView;
		TextView mTextView;
	}

	/**
	 * 动态插入节点
	 * 
	 * @param position
	 * @param string
	 */
	public void addExtraNodes(int position, String string) {
		Node node = mAllVisibleNodes.get(position);
		int i = mAllNodes.indexOf(node);
		Node extraNode = new Node(-1, node.getId(), string);
		extraNode.setParent(node);
		node.getChildren().add(extraNode);
		mAllNodes.add(i + 1, extraNode);
		mAllVisibleNodes = TreeHelper.filterVisibleNodes(mAllNodes);
		notifyDataSetChanged();
	}

}
