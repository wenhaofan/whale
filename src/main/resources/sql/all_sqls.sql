统一管理所有的sql

共享的sql
#namespace("share")
#include("share.sql")
#end

前台sql
#namespace("index")
#include("/front/index.sql")
#end
#namespace("agentUser")
#include("/front/agentUser.sql")
#end
登录sql
#namespace("login")
#include("/front/login.sql");
#end
#namespace("meta")
#include("/front/meta.sql")
#end
文章sql
#namespace("article")
#include("/front/article.sql");
#end
#namespace("comment")
#include("/front/comment.sql");
#end
后台sql
#namespace("adminArticle")
#include("/back/article.sql")
#end
#namespace("adminKv")
#include("/back/kv.sql")
#end
#namespace("statistic")
#include("/back/statistic.sql")
#end
#namespace("disk")
#include("/back/disk.sql")
#end


加载通用sql组件
#include("common.sql");