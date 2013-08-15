<?php

$debug = false;
$debugResult = "<data>";
header("Content-type:text/xml; Charset=utf-8");
require_once("chat_constants.php"); 
require_once("chat_commonfunction.php"); 
require_once("chat_functions.php"); 
require_once("chat_setting_mysql.php");


    $table  = $_GET["table"];
    $uid = $_GET["uid"];
    $chat_fuidstr = $_GET["fuidstr"];
    
    $conn = mysql_connect($dbhost, $dbuser, $dbpass) or die('Error with MySQL connection');
	mysql_query("SET NAMES `UTF8`");
	mysql_query("SET CHARACTER_SET_CLIENT=utf8");
	mysql_query("SET CHARACTER_SET_RESULTS=utf8"); 
    mysql_select_db($dbname);
    
    $sql = "";
    if($table == "member" && $uid != ""){
        $sql = "SELECT * FROM "."uc_members"." WHERE uid = ".$uid;
    }else if($table == "chat" && $uid != ""){ //via uid
        //$sql = "SELECT * FROM ".$table." WHERE `fuidstr` like '%".$uid."%' or `uid` = ".$uid;
        //$sql = "SELECT c.* 
        //	   FROM chat as c 
        //	   WHERE c.fuidstr like '%".$uid."%' 
        //	   GROUP BY c.fuidstr 
        //	   ORDER BY c.dateline ASC";
        $sql = "SELECT *
                FROM (
                    SELECT *
                        FROM uc_chat as c 
                        WHERE c.state = 'normal' and (
                            c.fuidstr REGEXP '^".$uid.",' or 
                            c.fuidstr REGEXP '^".$uid."$' or 
                            c.fuidstr REGEXP ',".$uid.",' or 
                            c.fuidstr REGEXP ',".$uid."$')  
			ORDER BY dateline DESC
                ) AS chats
		GROUP BY fuidstr
		ORDER BY dateline DESC ";
    }else if($table == "chat" && $chat_fuidstr != ""){ //via fuidstr
        $sql = 
        "SELECT c.*, ( 
            select distinct(m.username) 
            from uc_members as m, uc_chat as ci 
            WHERE m.uid = ci.fuid and ci.fuid = c.fuid and ci.state = 'normal' ) as username 
        FROM uc_chat as c, uc_members as m 
        WHERE m.uid in (c.fuidstr) and `fuidstr` = '".$chat_fuidstr."'  and c.state = 'normal' 
        ORDER BY dateline ASC
        ";
    }else if($table == "friend" && $uid != ""){
        $sql = 
        "SELECT @rownum:=@rownum+1 as _id, f.uid, f.fuid, f.fusername, f.gid, m.email as femail 
            , us.spacenote 
        FROM uchome_friend as f, uc_members as m, uchome_spacefield as us, (SELECT @rownum:=0) as r 
        WHERE f.fuid = m.uid and f.fuid = us.uid and f.uid = '".$uid."';";
    }else if($table == "searchAccount"){
    	$username = $_GET["username"];
    	if( $username != "" ){
        	//$sql = "SELECT uid, username, email FROM "."uc_members WHERE username = '".$username."'";
        	$sql = "SELECT us.uid, us.resideprovince, us.residecity, us.birthmonth, us.birthday, us.sex, us.spacenote, um.username FROM "."uchome_spacefield as us ".", uc_members as um "." WHERE um.uid = us.uid and `username` = '".$username."'";
    	}
    }else if($table == "searchAreaCount"){
    	$resideprovince = $_GET["resideprovince"];
    	//$resideprovince = $_POST["resideprovince"];
    	$debugResult = $debugResult.addDOM($resideprovince, "row");
    	if( $resideprovince != "" ){
    		$sql = "SELECT count(uid) as count FROM  "."uchome_spacefield"." WHERE `resideprovince` = '".$resideprovince."'";
    		
    	}
        
    }else if($table == "searchArea"){
    	$resideprovince = $_GET["resideprovince"];
    	$index = $_GET["index"];
    	$record = $_GET["record"];
    	if( $resideprovince != "" ){
    		$sql = "SELECT us.uid, us.resideprovince, us.residecity, us.birthmonth, us.birthday, us.sex, us.spacenote, um.username FROM "."uchome_spacefield as us ".", uc_members as um "." WHERE um.uid = us.uid and `resideprovince` = '".$resideprovince."'"
    				." LIMIT ".$index." ,".$record;
    		
    	}
        
    }
    
    
    
    if( !$debug ){
    	if( $sql != "" ){
    		$result = mysql_query($sql) or die("<data><row>".'MySQL query error'."</row></data>");
    		$newarray = array(); 
    		while($row=mysql_fetch_assoc($result)) {
                    array_push($newarray, $row);
                } 
    		echo dataToXML($newarray);
    	}else{
    		$debugResult = $debugResult.addDOM("No Matched Action!", "row");
    		$debugResult = $debugResult."</data>";
    		echo $debugResult;
    	}
    	
    }else{
    	$debugResult = $debugResult.addDOM($sql, "row");
    	$debugResult = $debugResult."</data>";
    	echo $debugResult;
    }
?>