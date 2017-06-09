package com.z.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.z.constant.ExecuteStatus;
import com.z.constant.SysConfig;
import com.z.repository.mongodb.entity.IconEntity;
import com.z.repository.mongodb.entity.MenuEntity;
import com.z.repository.mongodb.entity.NickNameEntity;
import com.z.repository.mongodb.entity.RuleEntity;
import com.z.repository.mongodb.entity.SysConfigEntity;
import com.z.repository.mongodb.entity.TaskEntity;
import com.z.repository.mongodb.entity.UserEntity;
import com.z.service.DaoService.Condition;
import com.z.util.DateUtil;

@Service
public class InitService {
	private static final Logger logger = Logger.getLogger(InitService.class);
	@Autowired
	private DaoService daoService;

	public void initMenus() {
		List<MenuEntity> menuEntities = new ArrayList<MenuEntity>();
		MenuEntity entity = new MenuEntity();
		entity.setId("index");
		entity.setSeq(0);
		entity.setNameZh("首页");
		entity.setNameEn("HOME");
		entity.setPath("/");
		menuEntities.add(entity);

		entity = new MenuEntity();
		entity.setId("product");
		entity.setSeq(1);
		entity.setNameZh("产品");
		entity.setNameEn("PRODUCT");
		entity.setPath("/product");
		menuEntities.add(entity);

		entity = new MenuEntity();
		entity.setId("activity");
		entity.setSeq(1);
		entity.setNameZh("活动");
		entity.setNameEn("ACTIVITY");
		entity.setPath("/activity");
		menuEntities.add(entity);

		entity = new MenuEntity();
		entity.setId("credit");
		entity.setSeq(2);
		entity.setNameZh("信誉");
		entity.setNameEn("CREDIT");
		entity.setPath("/credit");
		menuEntities.add(entity);

		daoService.delete(null, MenuEntity.class);
		daoService.insert(menuEntities, MenuEntity.class);
	}

	public void initSysConfigs() {
		List<SysConfigEntity> entities = new ArrayList<SysConfigEntity>();
		SysConfigEntity entity = null;

		entity = new SysConfigEntity();
		entity.setId(SysConfig.BASE_URL.getId());
		entity.setDesc(SysConfig.BASE_URL.getDesc());
		entity.setValue("http://localhost:8280");
		entities.add(entity);

		entity = new SysConfigEntity();
		entity.setId(SysConfig.FILE_URL.getId());
		entity.setDesc(SysConfig.FILE_URL.getDesc());
		entity.setValue("/file");
		entities.add(entity);

		entity = new SysConfigEntity();
		entity.setId(SysConfig.FILE_PATH.getId());
		entity.setDesc(SysConfig.FILE_PATH.getDesc());
		entity.setValue("/D:/home/app/file");
		entities.add(entity);

		entity = new SysConfigEntity();
		entity.setId(SysConfig.IMAGE_FOLDER.getId());
		entity.setDesc(SysConfig.IMAGE_FOLDER.getDesc());
		entity.setValue("/image");
		entities.add(entity);

		entity = new SysConfigEntity();
		entity.setId(SysConfig.GIF_FOLDER.getId());
		entity.setDesc(SysConfig.GIF_FOLDER.getDesc());
		entity.setValue("/gif");
		entities.add(entity);

		entity = new SysConfigEntity();
		entity.setId(SysConfig.VIDEO_FOLDER.getId());
		entity.setDesc(SysConfig.VIDEO_FOLDER.getDesc());
		entity.setValue("/video");
		entities.add(entity);

		entity = new SysConfigEntity();
		entity.setId(SysConfig.OTHER_FOLDER.getId());
		entity.setDesc(SysConfig.OTHER_FOLDER.getDesc());
		entity.setValue("/other");
		entities.add(entity);

		daoService.delete(null, SysConfigEntity.class);
		daoService.insert(entities, SysConfigEntity.class);
	}

	public void initSystemUsers() {
		Condition condition = new Condition();
		condition.addParam("status", "=", "1");
		List<NickNameEntity> nicknames = daoService.query(condition, NickNameEntity.class);
		List<UserEntity> entities = new ArrayList<UserEntity>();
		Date now = new Date();
		int i = 0;
		for (; i < 100; i++) {
			UserEntity entity = new UserEntity();
			String nickname = i + 1 > nicknames.size() ? "老司机" + (i + 1) : nicknames.get(i).getId();

			entity.setId(nickname);
			entity.setUsertype("S");
			entity.setPassword("Password");
			entity.setRegisterDate(DateUtil._DAY.format(DateUtil.addDays(now,
					0 - (int) (Math.random() * 200))));
			entities.add(entity);
		}
		daoService.delete(null, UserEntity.class);
		daoService.insert(entities, UserEntity.class);

		if (i + 1 > nicknames.size()) {
			nicknames = nicknames.subList(0, i + 1);
		}
		for (NickNameEntity nickName : nicknames) {
			nickName.setStatus("2");
		}
		daoService.save(nicknames);
	}

	public void initNickNames() {
		String namesString = "爬上墙头等红杏,骑牛撞交警,WCCEO,一般一般全国第三," + "纯情小鸭鸭,一脸的美人痣,阏,猪是的念来过倒,帅的惊动了如来佛,"
				+ "帅的惊动了联合国,马克龙夸我帅,非洲小白脸,日后再说,唐伯虎点蚊香," + "朕射你无罪,卖血上网,朝三暮四郎,我一贱你就笑,穷也站在富人堆里,"
				+ "化腐朽为绵掌,请偷我对门,今夜酷寒不宜裸奔,车到山前是死路,怕瓦落地," + "一年硬两次一次硬半年,他们逼我做卧底,小泉，纯一狼,中国制造,贫僧夜探青楼,"
				+ "背着头满街走,我就不信注册不上,我要一桶浆糊,老衲要射了,手起刀落人抬走,"
				+ "史珍香,杜子腾,范统,朱逸群,矫厚根,刘产,杨伟,范剑,秦寿生,庞光,杜琦燕,"
				+ "杨毅之,赖月京,任垚,魏生津,苟学玑,苟寒食,毕云涛,朱月土皮,木木昆,夏柳,"
				+ "魏安复,朱石,杨滇峰,殷道研,马辟京,罗密欧和猪过夜,许仙敢草蛇,按键伤人,"
				+ "郑射你无罪,我猥琐、我自豪,笑熬浆糊,两母牛盘腿对坐－比较牛比！,咬住JJ猛舔不放,"
				+ "高效安全无污染,本人已死,有事烧纸,煮人为快乐之本,恩……快点噢YES,温柔一刀,"
				+ "少年先疯队队长,老衲法号帅哥,__标准衰哥,加勒比海带,女娲补锅,无情的泡面,"
				+ "农夫三拳有点痛,动力火锅www.yw11.com,亚里士缺德,射鲸英雄传,神经侠侣,"
				+ "少年包青蛙,鸭梨山大,掐死你的温柔,妹妹你坐船头,风吹裙子屁屁凉,专摸魔族大MIMI,"
				+ "哈里波特别,洞房不败,含笑半步颠,踩姑娘的小蘑菇,六卖神剑,笑熬浆糊拎壶冲,主席夸我聊天聊得好~,"
				+ "以茎制洞,青春特么骚っ,万奶迎北京,当Dog遇到God,笑熬糨糊拎壶冲,有尽有过，腻了。,"
				+ "多看A片不好,卖精来上网,帅的被人砍,辣B！小心！,和尚洗头用飘柔,和尚不色叫嫖客,"
				+ "哈里波特大,撑水，扁,驴，修炼,狂飙的蜗牛,妈，他摸我。,茎侯佳阴,卖女孩的小火柴,麻花藤,"
				+ "℡少爷，你好坏~,谈情不谈钱§,极品风骚男,拿老公换糖糖吃,小泽·玛莉婭,回家养猪算勒,"
				+ "纠结菂丶女λ,夶埱ぃ別看ㄋ,上帝の诱惑,懵懂的小屁孩※,一天到晚红烧的鱼,卖女孩的小火柴,"
				+ "鲁智深三打白骨精,丑的拖网速,黑洲非人,爱在戏院前,我什么都有，就是没钱,我拿什么整死你，"
				+ "我的爱人,比是一样的比，脸上见高低,老婆·别罚我,真心换黑心,你给我过来,愤怒的老鸟,"
				+ "洞房不败,你一贱我就笑,咬字分开念,缝小肛,怀念在地球的日子,大能猫,做贼肾虚,成吉思春,"
				+ "神鲸大侠,太平间公主,绞尽乳汁,深入且持久,一脸的美人痣,老庄孙子,独脚戏,夜袭寡女村,"
				+ "强壮的,微笑依旧「别样骚,n1↘扯淡,︿奥特曼打小怪︿,很烦丶很纠结,娶个太阳暖被窝,"
				+ "早已戒网﹌,好听搞笑网名名字大全,冷销魂∝,走结婚去，莪请客,站住！打劫棒棒糖,欧巴~快到碗里来,"
				+ "ε喵喵,你有多红，比姨妈还红么,[大众女神经],生活再累也要屁颠屁颠的过,蹲在坟头勾引鬼ぃ,"
				+ "槑纸°,失败是成功之后妈,当姚明和郭敬明站在一起时,真为你的智商捉鸡,别勾引我，小心我从了你,"
				+ "该用户已下线,请只蚂蚁当靠山,你若安好，那还得了,你若安好，便是老天瞎了眼,带伱装逼带伱飞,"
				+ "⑧格牙路罒▽罒,疯人院ヽ因迩更精彩,把班主任捐给灾区,搗蛋鬼￢ε￢,看，有灰碟！,沵和她吻吧爱吧滚吧去死吧";
		String[] names = namesString.split(",");
		Set<String> set = new HashSet<String>();
		for (String name : names) {
			name = name.replaceAll("[^a-zA-Z0-9\u4e00-\u9fa5]", "");
			if (name.getBytes().length > 20) {
				continue;
			}
			set.add(name);
		}

		List<NickNameEntity> entities = new ArrayList<NickNameEntity>();
		NickNameEntity entity = null;
		for (String name : set) {
			entity = new NickNameEntity();
			entity.setId(name);
			entity.setStatus("1");
			entities.add(entity);
		}

		daoService.delete(null, NickNameEntity.class);
		daoService.insert(entities, NickNameEntity.class);
	}

	public void initRules() {
		List<RuleEntity> entities = new ArrayList<RuleEntity>();
		RuleEntity entity = null;

		entity = new RuleEntity();
		entity.setId("A1");
		entity.setUrl("http://neihanshequ.com/joke/?is_json=1&app_name=neihanshequ_app&${0}");
		entity.setSplitParam("1494223658");
		entity.setForwardParam("1494223658");
		entity.setBackwardParam("1494223658");
		entity.setStepSize(30);
		entities.add(entity);

		entity = new RuleEntity();
		entity.setId("A2");
		entity.setUrl("http://neihanshequ.com/pic/?is_json=1&app_name=neihanshequ_app&${0}");
		entity.setSplitParam("1494223658");
		entity.setForwardParam("1494223658");
		entity.setBackwardParam("1494223658");
		entity.setStepSize(30);
		entities.add(entity);

		entity = new RuleEntity();
		entity.setId("A3");
		entity.setUrl("http://neihanshequ.com/video/?is_json=1&app_name=neihanshequ_app&${0}");
		entity.setSplitParam("1494223658");
		entity.setForwardParam("1494223658");
		entity.setBackwardParam("1494223658");
		entity.setStepSize(30);
		entities.add(entity);

		entity = new RuleEntity();
		entity.setId("B");
		entity.setUrl("http://www.budejie.com/detail-${0}.html");
		entity.setSplitParam("24890821");
		entity.setForwardParam("24890821");
		entity.setBackwardParam("24890820");
		entity.setStepSize(20);
		entities.add(entity);

		entity = new RuleEntity();
		entity.setId("C");
		entity.setUrl("http://www.qiushibaike.com/article/${0}");
		entity.setSplitParam("118997140");
		entity.setForwardParam("118997140");
		entity.setBackwardParam("118997139");
		entity.setStepSize(20);
		entities.add(entity);

		daoService.delete(null, RuleEntity.class);
		daoService.insert(entities, RuleEntity.class);
	}

	public void initIcons() {
		List<IconEntity> entities = new ArrayList<IconEntity>();
		IconEntity entity = new IconEntity();
		entity.setId("1000001");
		entity.setUrl("/img/logo.jpg");
		entity.setOriginalUrl("/img/logo.jpg");
		entity.setPublisher("SYSTEM");
		entities.add(entity);
		daoService.delete(null, IconEntity.class);
		daoService.insert(entities, IconEntity.class);
	}

	public void initTasks() {
		List<TaskEntity> entities = new ArrayList<TaskEntity>();
		TaskEntity entity = null;

		entity = new TaskEntity();
		entity.setId("JokeTask");
		entity.setExecuteStatus(ExecuteStatus.SUCCESS.name());
		entities.add(entity);

		entity = new TaskEntity();
		entity.setId("FilePullTask");
		entity.setExecuteStatus(ExecuteStatus.SUCCESS.name());
		entities.add(entity);

		entity = new TaskEntity();
		entity.setId("FileMarkTask");
		entity.setExecuteStatus(ExecuteStatus.SUCCESS.name());
		entities.add(entity);

		daoService.delete(null, TaskEntity.class);
		daoService.insert(entities, TaskEntity.class);
	}
}
