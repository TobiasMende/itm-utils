/**********************************************************************************************************************
 * Copyright (c) 2010, Institute of Telematics, University of Luebeck                                                 *
 * All rights reserved.                                                                                               *
 *                                                                                                                    *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the   *
 * following conditions are met:                                                                                      *
 *                                                                                                                    *
 * - Redistributions of source code must retain the above copyright notice, this list of conditions and the following *
 *   disclaimer.                                                                                                      *
 * - Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the        *
 *   following disclaimer in the documentation and/or other materials provided with the distribution.                 *
 * - Neither the name of the University of Luebeck nor the names of its contributors may be used to endorse or promote*
 *   products derived from this software without specific prior written permission.                                   *
 *                                                                                                                    *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, *
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE      *
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,         *
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE *
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF    *
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY   *
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.                                *
 **********************************************************************************************************************/

package de.uniluebeck.itm.tr.util;

import com.google.common.collect.ImmutableList;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Abstract base class for classes that have to inform listeners about events. This class is thread-safe.
 *
 * @param <T> the listener interface under which listeners register themselves.
 */
@SuppressWarnings("unused")
public abstract class AbstractListenable<T> implements Listenable<T> {

	protected ImmutableList<T> listeners = (ImmutableList<T>) ImmutableList.builder().build();

	@Override
	@SuppressWarnings("unchecked")
	public void addListener(T listener) {

		checkNotNull(listener);

		// assure listener is only contained once
		ImmutableList.Builder<Object> builder = ImmutableList.builder();
		for (T t : listeners) {
			if (t != listener) {
				builder.add(t);
			}
		}
		builder.add(listener);
		
		listeners = (ImmutableList<T>) builder.build();
	}

	@Override
	@SuppressWarnings("unchecked")
	public void removeListener(T listener) {

		checkNotNull(listener);

		ImmutableList.Builder<Object> listBuilder = ImmutableList.builder();
		for (T t : listeners) {
			if (t != listener) {
				listBuilder.add(t);
			}
		}

		listeners = (ImmutableList<T>) listBuilder.build();
	}

}
