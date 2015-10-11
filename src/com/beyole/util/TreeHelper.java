package com.beyole.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.beyole.treeview.R;
import com.beyole.util.annotation.TreeNodeId;
import com.beyole.util.annotation.TreeNodeLabel;
import com.beyole.util.annotation.TreeNodePid;

public class TreeHelper {

	/**
	 * 将用户的数据转换为树形数据
	 * 
	 * @param datas
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static <T> List<Node> convertData2Nodes(List<T> datas)
			throws IllegalArgumentException, IllegalAccessException {
		List<Node> nodes = new ArrayList<Node>();
		Node node = null;
		
		for (T t : datas) {
			int id = -1;
			int pid = -1;
			String label = null;
			Class clazz = t.getClass();
			
			//不能写成getFields(),不然显示成null
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				if (field.getAnnotation(TreeNodeId.class) != null) {
					field.setAccessible(true);
					id = field.getInt(t);
				}
				if (field.getAnnotation(TreeNodePid.class) != null) {
					field.setAccessible(true);
					pid = field.getInt(t);
				}
				if (field.getAnnotation(TreeNodeLabel.class) != null) {
					field.setAccessible(true);
					// 没有getString 就直接用get来获取
					label = (String) field.get(t);
				}
			}//for end
			Log.e("Node节点信息:",id+pid+label);
			node = new Node(id, pid, label);
			nodes.add(node);
		}//for end

		// 设置节点之间的关系
		for (int i = 0; i < nodes.size(); i++) {
			Node n = nodes.get(i);
			for (int j = i + 1; j < nodes.size(); j++) {
				Node m = nodes.get(j);
				if (m.getpId() == n.getId()) {
					m.setParent(n);
					n.getChildren().add(m);
				} else if (m.getId() == n.getpId()) {
					n.setParent(m);
					m.getChildren().add(n);
				}
			}
		}

		for (Node n : nodes) {
			setNodeIcon(n);
		}
		
		Log.e("nodes的size", nodes.size()+"");
		return nodes;
	}

	/**
	 * 获取排序后的数据
	 * 
	 * @param datas
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static <T> List<Node> getSortedNodes(List<T> datas,
			int defaultExpandLevel) throws IllegalArgumentException,
			IllegalAccessException {
		List<Node> result = new ArrayList<Node>();
		List<Node> nodes = convertData2Nodes(datas);

		// 获取树的根节点

		List<Node> rootNodes = getRootNodes(nodes);

		Log.e("树的根节点个数为",""+rootNodes.size());
		for (Node node : rootNodes) {
			addNode(result, node, defaultExpandLevel, 1);
		}
		Log.e("getSortedNodes的size、", result.size()+"");
		return result;
	}

	/**
	 * 把一个节点的所有孩子节点放入result中
	 * 
	 * @param result
	 * @param node
	 * @param defaultExpandLevel
	 * @param currentLevel
	 */
	private static void addNode(List<Node> result, Node node,
			int defaultExpandLevel, int currentLevel) {

		result.add(node);
		if (defaultExpandLevel >= currentLevel) {
			node.setExpand(true);
		}
		if (node.isLeaf()) {
			return;
		} else {
			for (int i = 0; i < node.getChildren().size(); i++) {
				// 利用递归来实现将所有的子节点加入到result中
				addNode(result, node.getChildren().get(i), defaultExpandLevel,
						currentLevel + 1);
			}
		}
	}

	/**
	 * 过滤出可见的节点
	 * 
	 * @param nodes
	 * @return
	 */
	public static List<Node> filterVisibleNodes(List<Node> nodes) {
		List<Node> resultNodes = new ArrayList<Node>();
		for (Node n : nodes) {
			if (n.isRoot() || n.isParentExpand()) {
				setNodeIcon(n);
				resultNodes.add(n);
			}
		}
		return resultNodes;
	}

	/**
	 * 从所有节点中过滤出根节点
	 * 
	 * @param nodes
	 * @return
	 */
	private static List<Node> getRootNodes(List<Node> nodes) {
		List<Node> rootNodes = new ArrayList<Node>();
		for (Node node : nodes) {
			if (node.isRoot()) {
				rootNodes.add(node);
			}
		}
		return rootNodes;
	}

	/**
	 * 为node设置图标
	 * 
	 * @param n
	 */
	private static void setNodeIcon(Node n) {
		if (n.getChildren().size() > 0 && n.isExpand()) {
			n.setIcon(R.drawable.tree_ex);
		} else if (n.getChildren().size() > 0 && !n.isExpand()) {
			n.setIcon(R.drawable.tree_ec);
		} else {
			n.setIcon(-1);
		}
	}
}
