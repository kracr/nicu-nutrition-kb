package com.inicu.postgres.serviceImpl;




import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
//import com.oxyent.inicu.models.AddTemplate;
import com.inicu.models.DocTemplate;
import com.inicu.models.ResponseMessageObject;
import com.inicu.models.TemplateJSON;
import com.inicu.postgres.dao.SystematicDAO;
import com.inicu.postgres.dao.TemplateDao;
import com.inicu.postgres.entities.DoctorNoteTemplates;
import com.inicu.postgres.service.LogsService;
import com.inicu.postgres.service.TemplateService;
import com.inicu.postgres.utility.BasicConstants;
import com.inicu.postgres.utility.BasicUtils;
import com.inicu.postgres.utility.InicuPostgresUtililty;


@Repository
public class TemplateImp implements TemplateService 
{
	
	@Autowired
	InicuPostgresUtililty inicuPostgresUtility;
	
	@Autowired
	SystematicDAO sysDAO;
	@Autowired
	TemplateDao tempDao;
	
	@Autowired
	LogsService logService;
	
	ObjectMapper mapper = new ObjectMapper();

	
/*
	@Override
	 public boolean addTemplate( temp) {
		// TODO Auto-generated method stub
		
	return true;
	} */

	@Override
	public DocTemplate viewTemplate(String type1) {
		// TODO Auto-generated method stub
		

		DocTemplate json = new DocTemplate();
			List<TemplateJSON> templateList = new ArrayList<TemplateJSON>();
			//select template data from database
			String query = "SELECT temp FROM DoctorNoteTemplates AS temp where templateType='"+type1+"' ORDER BY doctemplateid" ;
			try{
				List<DoctorNoteTemplates> resultSet = tempDao.executeObjectQuery(query);
				if(!BasicUtils.isEmpty(resultSet)){
					for(DoctorNoteTemplates v: resultSet){
						TemplateJSON temp = new TemplateJSON();
						
						String id =String .valueOf( v.getDoctemplateid());
						if(!BasicUtils.isEmpty(id)){
								temp.settemplateid(id);													
						}
						
						String creationtime =String .valueOf( v.getCreationtime());
						if(!BasicUtils.isEmpty(creationtime)){
								temp.setCreationTime(creationtime);													
						}
						String modificationtime =String .valueOf( v.getModificationtime());
						if(!BasicUtils.isEmpty(modificationtime)){
								temp.setModificationtime(modificationtime);													
						}
						
						String header = v.getTemplateHeader();
						if(!BasicUtils.isEmpty(header)){
							temp.setTemplateHeader(header);						
						}

						String type = v.getTemplateType();
						if(!BasicUtils.isEmpty(type)){
							temp.setTemplateType(type);						
						}

						String body = v.getTemplateBody();
						if(!BasicUtils.isEmpty(body)){
							try{
								temp.setTemplateBody(body);													
							}catch(Exception e){
								e.printStackTrace();
							}
						}

					/*	Timestamp create = v.getCreationtime();
						if(!BasicUtils.isEmpty(create)){
							temp.setCreationTime(create);						
						}

						Timestamp modify = v.getModificationtime();
						if(!BasicUtils.isEmpty(modify)){
							temp.setModificationtime(modify);						
						}

						*/

						//add each resultset in List
					     templateList.add(temp);
					}

					//get dropdown values
					

					//for empty template JSON
					json.setListTemplate(templateList);
					//dummy json for entering the new data
					TemplateJSON emptyTemplate = new TemplateJSON();
				emptyTemplate.settemplateid("");
					emptyTemplate.setTemplateHeader("");
				     emptyTemplate.setTemplateBody("");
					emptyTemplate.setTemplateType("");
				//	emptyTemplate.setCreationTime("");
					//emptyTemplate.setModificationTime("");
				
					json.setTemplate(emptyTemplate);

				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return  json;
		}


	@Override
	public ResponseMessageObject addTemplate(TemplateJSON temp,String userId) {
		
		ResponseMessageObject obj = new ResponseMessageObject();
		obj.setType(BasicConstants.MESSAGE_SUCCESS);
		obj.setMessage("save successful");
		try{

			DoctorNoteTemplates notetemplate = new DoctorNoteTemplates();
			//Long id=Long.parseLong(temp.gettemplateid());
			if(!BasicUtils.isEmpty(temp.gettemplateid())){
				Long id=Long.parseLong(temp.gettemplateid());
				notetemplate.setDoctemplateid(id);
				DoctorNoteTemplates templ = inicuPostgresUtility.getEntityManager().find(DoctorNoteTemplates.class,Long.parseLong(temp.gettemplateid()));
				notetemplate.setCreationtime(templ.getCreationtime());
			}
			
//				notetemplate.setDoctemplateid(Long.parseLong(temp.gettemplateid()));
				
		
			String header = temp.getTemplateHeader();
			if(!BasicUtils.isEmpty(header)){
				notetemplate.setTemplateHeader(header);			
			}

			String type = temp.getTemplateType();
			if(!BasicUtils.isEmpty(type)){
				notetemplate.setTemplateType(type);			
			}

			String body = temp.getTemplateBody();
			if(!BasicUtils.isEmpty(body)){
				notetemplate.setTemplateBody(body);			
			}

  /*       String query2="select max(doctemplateid)+1 from DoctorNoteTemplates";
         List<Object> resultSet = tempDao.executeObjectQuery(query2);
         if(!BasicUtils.isEmpty(resultSet)){
        	 notetemplate.setDoctemplateid(Long.parseLong(resultSet.get(0).toString()));
         }
	*/		
			if(userId!=null)
			notetemplate.setLoggedUser(userId);

			tempDao.saveObject(notetemplate);
			
			//save logs
			String desc = mapper.writeValueAsString(notetemplate);
			String action = BasicConstants.INSERT;
			if(temp.isEdit()){
				action = BasicConstants.UPDATE;
			}
			
			
			String loggeduser = null;
			if(!BasicUtils.isEmpty(userId)){
				loggeduser = userId;					
			}else{
				loggeduser = "1234"; // setting dummy user as of now needs to be removed
			}
			String docid;
			if(!BasicUtils.isEmpty(temp.getDoctorId()))
			{
				docid=temp.getDoctorId();
			}
			else
			{
				docid=null;
			}
			String pageName = BasicConstants.Doctor_Note_Template;
			
			logService.saveLog(desc,action,loggeduser,docid,pageName);

		} catch (Exception e) {
			obj.setType(BasicConstants.MESSAGE_FAILURE);
			obj.setMessage(e.getMessage());
			e.printStackTrace();
			return obj;
		}

		return obj;
		// TODO Auto-generated method stub
		
		
	}


	@Override
	public ResponseMessageObject deleteTemplate(String tempId,String loggeduser) {
		// TODO Auto-generated method stub
		Long id=Long.parseLong(tempId);
		ResponseMessageObject obj = new ResponseMessageObject();
		obj.setType(BasicConstants.MESSAGE_SUCCESS);
		obj.setMessage("delete successfully");
		try {
			//record of particular is delete from database
			
					
		String delQuery="DELETE FROM DoctorNoteTemplates where doctemplateid="+id;
		
		
			tempDao.delObject(delQuery);
			//save logs
			String desc = mapper.writeValueAsString(delQuery);
			String action = BasicConstants.DELETE;			
			String pageName = BasicConstants.Doctor_Note_Template;
			
			logService.saveLog(desc,action,loggeduser,"",pageName);
		}
		catch(Exception e)
		{
				obj.setType(BasicConstants.MESSAGE_FAILURE);
				obj.setMessage(e.getMessage());
				e.printStackTrace();
				return obj;
		}
		
		return obj;
	}
	public TemplateJSON getTemplateType(String tempId)
	{
		TemplateJSON temp=new TemplateJSON();
		// TODO Auto-generated method stub
				Long id=Long.parseLong(tempId);
				try
				{
		String query="select templateType from DoctorNoteTemplates where doctemplateid="+id;
				List<Object> tempType=tempDao.executeObjectQuery(query);
				if(!BasicUtils.isEmpty(tempType)){
					String type=tempType.get(0).toString();
					 temp.setTemplateType(type);
					 System.out.println(temp.getTemplateType());
				}
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return temp;
				}
				
			        
			return temp;
	}
	}
	

