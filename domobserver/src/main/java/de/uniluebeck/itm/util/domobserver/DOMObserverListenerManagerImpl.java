package de.uniluebeck.itm.util.domobserver;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import org.w3c.dom.Node;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class DOMObserverListenerManagerImpl implements DOMObserverListenerManager {

	private final Lock listenerMapLock = new ReentrantLock();

	private Map<DOMObserverListener, Node> listenerMap = Maps.newHashMap();

	@Override
	public Node getLastDOM(final DOMObserverListener listener) {
		listenerMapLock.lock();
		try {
			if (!listenerMap.containsKey(listener)) {
				throw new IllegalArgumentException("The listener instance " + listener + " is not registered!");
			}
			return listenerMap.get(listener);
		} finally {
			listenerMapLock.unlock();
		}
	}

	@Override
	public void updateLastDOM(final DOMObserverListener listener, final Node updatedDOM) {
		listenerMapLock.lock();
		try {
			if (!listenerMap.containsKey(listener)) {
				throw new IllegalArgumentException("The listener instance " + listener + " was not yet registered!");
			}
			listenerMap.put(listener, updatedDOM);
		} finally {
			listenerMapLock.unlock();
		}
	}

	@Override
	public ImmutableList<DOMObserverListener> getListeners() {
		ImmutableList.Builder<DOMObserverListener> builder = ImmutableList.builder();
		listenerMapLock.lock();
		try {
			builder.addAll(listenerMap.keySet());
		} finally {
			listenerMapLock.unlock();
		}
		return builder.build();
	}

	@Override
	public void addListener(final DOMObserverListener listener) {
		listenerMapLock.lock();
		try {
			if (listenerMap.containsKey(listener)) {
				throw new IllegalArgumentException("The listener instance " + listener + " is already registered!");
			}
			listenerMap.put(listener, null);
		} finally {
			listenerMapLock.unlock();
		}
	}

	@Override
	public void removeListener(final DOMObserverListener listener) {
		listenerMapLock.lock();
		try {
			listenerMap.remove(listener);
		} finally {
			listenerMapLock.unlock();
		}
	}
}
