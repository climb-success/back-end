/*
 * Copyright (c) 2017 Du Tengfei. All Rights Reserved.
 */
package com.dao;

import com.content.Professional;

/**
 * StudentService.
 * @author <A HREF="mailto:dtfgongzuo@163.com">Du Tengfei</A>
 * @version 1.0, $Revision: 0$, $Date: Nov 18, 2017$
 * @since 1.0
 */
public interface ProfessionalService
{
    public Professional createProfessional(Professional professional);
    
    public Professional updateProfessional(Professional professional);
    
    public Professional getById(Integer id);
    
    public void deleteProfessional(Professional professional);

    public Professional[] getAllProfessional();
    
    public Professional getProfessionalByName(String name);
    
    public Professional getProfessionalByCode(String code);
}
