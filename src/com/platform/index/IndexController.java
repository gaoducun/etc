package com.platform.index;

import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class IndexController extends Controller {
	
	public void index() {
        System.out.println("---------已运行-----------");	
 		String boe_num=isParaExists("num")?getPara("num").toString():"";
 		if (boe_num.startsWith("EAS")&&boe_num.length()==13) {
 			System.out.println("-----------单号正确-----------");
 			/***
 			 * 查询凭证号
 			 */
 			
 			String sql = "select b.last_update_date 单据创建时间, \r\n" + 
 					"                 b.boe_num 单据编号, \r\n" + 
 					"                 lv.meaning 单据状态, \r\n" + 
 					"                 b.bp_count 单据张数, \r\n" + 
 					"                 b.attribute21 是否增值税报账, \r\n" + 
 					"                 e.employee_number 一卡通号, \r\n" + 
 					"                 b.employee_name 报账人, \r\n" + 
 					"                 b.employee_full_name 报账人全名, \r\n" + 
 					"                 t.company_name 核算单位名称, \r\n" + 
 					"                 v.BOE_TYPE_NAME 单据类型, \r\n" + 
 					"                 tv.operation_type_name 业务类型, \r\n" + 
 					"                 b.apply_amount 金额, \r\n" + 
 					"                 b.boe_abstract 单据说明 \r\n" + 
 					"            from \r\n" + 
 					"                 sie.sie_boe_headers b,            --单据头表 \r\n" + 
 					"                 fbp.fbp_employees e,\r\n" + 
 					"                 FBP_CALCU_UNIT t,                  --核算单位表 \r\n" + 
 					"                 sie_boe_types_v v,                    --单据类型表 \r\n" + 
 					"                 fbp.fbp_operation_types_tl tv, \r\n" + 
 					"                 (select * from FBP_LOOKUP_VALUES flv where flv.language='ZHS' and flv.lookup_type='BOE_STATUS') lv \r\n" + 
 					"           where \r\n" + 
 					"              b.employee_id=e.employee_id \r\n" + 
 					"             and b.boe_status=lv.lookup_code \r\n" + 
 					"             and b.org_id=t.oid \r\n" + 
 					"             and b.boe_type_id=v.BOE_TYPE_ID \r\n" + 
 					"             and b.operation_type_id=tv.operation_type_id \r\n" + 
 					"             and b.boe_num=?";
 			List<Record> blog = Db.find(sql,boe_num);
 			if (blog.isEmpty()) {
 				renderJson("msg", "查不到相关信息！");
			} else {
				renderJson(blog);
			}
 			
 		}else {
			renderJson("msg", "单号不正确");
		}
	}
	
	
	
	public void image() {
		System.out.println("---------已运行-----------");	
 		String boe_num=isParaExists("num")?getPara("num").toString():"";
 		if (boe_num.startsWith("EAS")&&boe_num.length()==13) {
 			System.out.println("-----------单号正确-----------");
 			/***
 			 * 查询凭证号
 			 */
 			
 			String sql = "select fo.image_number  as 影像编号,\r\n" + 
 					"       ee.employee_name as 上传人员,\r\n" + 
 					"       fo.upload_date   as 上传时间,\r\n" + 
 					"       cm.company_name  as 公司名称,\r\n" + 
 					"       va.meaning       as 影像状态,\r\n" + 
 					"       fo.image_count   as 影像数量\r\n" + 
 					"  from eid_image_infos   fo,\r\n" + 
 					"       fbp_employees_tl  ee,\r\n" + 
 					"       fbp_company_tl    cm,\r\n" + 
 					"       fbp_lookup_values va\r\n" + 
 					" where va.language != 'US'\r\n" + 
 					"   and fo.image_status = va.lookup_code\r\n" + 
 					"   and fo.upload_employee_id = ee.employee_id\r\n" + 
 					"   and fo.company_id = cm.company_id\r\n" + 
 					"   and fo.enabled_flag='Y'\r\n" + 
 					"   and fo.image_number=?\r\n" + 
 					"       group by fo.image_number,ee.employee_name,fo.upload_date,cm.company_name,va.meaning,fo.image_count\r\n";
 			List<Record> blog = Db.use("apps").find(sql,boe_num);
 			if (blog.isEmpty()) {
 				renderJson("查不到相关信息");
			} else {
				renderJson(blog.toString());
			}
 			
 		}else {
			renderJson("单号不正确");
		}
		
	}

}
