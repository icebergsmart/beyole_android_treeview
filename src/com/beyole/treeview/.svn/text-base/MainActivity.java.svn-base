package com.beyole.treeview;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.beyole.adapter.SimpleTreeListViewAdapter;
import com.beyole.model.FileBean;
import com.beyole.util.Node;
import com.beyole.util.adapter.TreeListViewAdapter.OnTreeNodeClickListener;

/**
 * android树形结构使用案例
 * 
 * @author Iceberg
 * 
 */
public class MainActivity extends Activity {

	private ListView mTree;
	private SimpleTreeListViewAdapter<FileBean> mAdapter;
	private List<FileBean> mDatas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTree = (ListView) findViewById(R.id.id_listview);
		initDatas();
		try {
			mAdapter = new SimpleTreeListViewAdapter<FileBean>(mTree, this, mDatas, 0);
			mTree.setAdapter(mAdapter);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		initEvents();
	}

	private void initEvents() {
		mAdapter.setTreeNodeOnclickListener(new OnTreeNodeClickListener() {

			@Override
			public void onClick(Node node, int position) {
				if (node.isLeaf()) {
					Toast.makeText(MainActivity.this, node.getName(), Toast.LENGTH_SHORT).show();
				}
			}
		});
		mTree.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
				// DialogFragment
				final EditText editText = new EditText(MainActivity.this);
				new AlertDialog.Builder(MainActivity.this).setTitle("Add Node").setView(editText).setPositiveButton("Sure", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						mAdapter.addExtraNodes(position, editText.getText().toString());
					}
				}).setNegativeButton("Cancel", null).show();
				return false;
			}

		});
	}

	private void initDatas() {
		mDatas = new ArrayList<FileBean>();
		FileBean bean = new FileBean(1, 0, "根目录1");
		mDatas.add(bean);
		bean = new FileBean(2, 0, "根目录2");
		mDatas.add(bean);
		bean = new FileBean(3, 0, "根目录3");
		mDatas.add(bean);
		bean = new FileBean(4, 1, "根目录1-1");
		mDatas.add(bean);
		bean = new FileBean(5, 1, "根目录1-2");
		mDatas.add(bean);
		bean = new FileBean(6, 5, "根目录1-2-1");
		mDatas.add(bean);
		bean = new FileBean(7, 3, "根目录3-1");
		mDatas.add(bean);
		bean = new FileBean(8, 3, "根目录3-2");
		mDatas.add(bean);
	}
}
