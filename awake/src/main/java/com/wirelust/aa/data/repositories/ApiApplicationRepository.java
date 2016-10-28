package com.wirelust.aa.data.repositories;

import com.wirelust.aa.data.model.ApiApplication;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;

/**
 * Date: 5/22/15
 *
 * @author T. Curran
 */
@Repository
public abstract class ApiApplicationRepository extends AbstractEntityRepository<ApiApplication, String> {

}
