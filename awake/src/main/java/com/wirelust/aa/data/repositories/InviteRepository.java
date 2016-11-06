package com.wirelust.aa.data.repositories;

import com.wirelust.aa.data.model.Account;
import com.wirelust.aa.data.model.Invite;
import org.apache.deltaspike.data.api.AbstractEntityRepository;

/**
 * Date: 05-Nov-2016
 *
 * @author T. Curran
 */
public abstract class InviteRepository extends AbstractEntityRepository<Invite, Long> {

	public abstract Invite findAnyByValue(String inValue);
}
