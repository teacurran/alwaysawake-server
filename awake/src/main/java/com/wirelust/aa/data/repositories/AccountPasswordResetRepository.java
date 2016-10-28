package com.wirelust.aa.data.repositories;

import com.wirelust.aa.data.model.AccountPasswordReset;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;

/**
 * Date: 5/22/15
 *
 * @author T. Curran
 */
@Repository
public abstract class AccountPasswordResetRepository extends AbstractEntityRepository<AccountPasswordReset, Long> {

}
