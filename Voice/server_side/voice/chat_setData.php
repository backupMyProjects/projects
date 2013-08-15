<?php

$debug = false;
$debugResult = "<result>";

header("Content-type:text/xml; Charset=UTF-8");
require_once("chat_functions.php"); 
require_once("chat_setting_mysql.php"); 
$conn = mysql_connect($dbhost, $dbuser, $dbpass, $dbname) or die('Error with MySQL connection');
	mysql_query("SET NAMES 'utf8'");
	mysql_query("SET CHARACTER_SET_CLIENT=utf8");
	mysql_query("SET CHARACTER_SET_RESULTS=utf8"); 
        mysql_select_db($dbname);

    $action  = $_POST["action"];
	
    if($action == "addContact"){
    	$uid = $_POST["uid"];
    	$fuid = $_POST["fuid"];
    	$status = $_POST["status"];
    	$fusername = $_POST["fusername"];
    	$gid = $_POST["gid"];
    	$dateline = $_POST["dateline"];
    
    	if ( $uid != "" && $fuid != "" && $fusername != "" && $gid != "" && $dateline != "" ){
            // // num is hot value, not important
            $sql = "
		INSERT INTO `uchome_friend` (
                    `uid`, `fuid`, `status`, `fusername`, `gid`, `num`, `dateline` )
		VALUES ('".$uid."', '".$fuid."', '".$status."', '".$fusername."', '".$gid."', '0', '".$dateline."');
		";
    	}
    
    }else if($action == "updateSpacenote"){ // uchome_spacefield as us
    	$uid = $_POST["uid"];
    	$spacenote = $_POST["spacenote"];
    	if ( $uid != "" && $spacenote != "" ){
    		$sql = "
    		UPDATE  `uchome_spacefield` SET  
    			`note` =  '".$spacenote."', 
    			`spacenote` =  '".$spacenote."' 
    		WHERE  `uchome_spacefield`.`uid` = '".$uid."' ;
    		";
    	}
    }else if($action == "updateResidecity"){ // uchome_spacefield as us
    	$uid = $_POST["uid"];
    	$residecity = $_POST["residecity"];
    	if ( $uid != "" && $residecity != "" ){
    		$sql = "
    		
    		";
    	}
    }else if($action == "insertChat"){
    	$fuidstr = $_POST["fuidstr"];
    	$fuid = $_POST["fuid"];
    	$said = $_POST["said"];
    	$type = $_POST["type"];
    	$room = $_POST["room"];
    	$dateline = $_POST["dateline"];
        
        if ( $fuidstr != "" && $fuid != "" && $said != "" && $type != "" && $dateline != "" ){
    	$sql = "INSERT INTO `uc_chat` (
    				`_id`, `fuidstr`, `fuid`, `said`, `type`, `room`, `dateline` )
				VALUES (
					NULL , '".$fuidstr."', '".$fuid."', '".$said."', '".$type."', '".$room."', '".$dateline."');";
		//echo $sql;
        }
    }else if($action == "deleteChatroom"){ // 
    	$fuidstr = $_POST["fuidstr"];
        
        if ( $fuidstr != "" ){
    	$sql = "UPDATE  `uc_chat` as uc
    			SET		`state` =  'delete' 
    			WHERE	uc.`fuidstr` = '".$fuidstr."';";
		//echo $sql;
		}
    }else if($action == "restoreChatroom"){ // 
    	$fuidstr = $_POST["fuidstr"];
        
        if ( $fuidstr != "" ){
    	$sql = "UPDATE  `uc_chat` as uc
    			SET		`state` =  'normal' 
    			WHERE	uc.`fuidstr` = '".$fuidstr."';";
		//echo $sql;
		}
    }else if($action == "deleteChat"){ // 
    	$id = $_POST["_id"];
        
        if ( $id != "" ){
    	$sql = "UPDATE  `uc_chat` as uc
    			SET		`state` =  'delete' 
    			WHERE	uc.`_id` = '".$id."';";
		//echo $sql;
		}
    }else if($action == "restoreChat"){ // 
    	$id = $_POST["_id"];
        
        if ( $id != "" ){
    	$sql = "UPDATE  `uc_chat` as uc
    			SET		`state` =  'normal' 
    			WHERE	uc.`_id` = '".$id."';";
		//echo $sql;
		}
    }
    
    //$result = mysqli_multi_query($conn, $sql) or die('MySQL query error');
    //$result = mysqli_store_result($conn);
    //$row = mysqli_fetch_row($result);
    //echo "<result>".$row[0]."</result>";
    
    if( !$debug ){
    	if( $sql != "" ){
    		$result = mysql_query($sql) or die("<result>".'MySQL query error'."</result>");
    		//$row = mysql_fetch_row($result);
    		echo "<result>".$result."</result>";
    	}else{
    		$debugResult = $debugResult.addDOM("No Matched Action!", "row");
    		$debugResult = $debugResult."</result>";
    		echo $debugResult;
    	}
    }else{
    	$debugResult = $debugResult.addDOM($sql, "row");
    	$debugResult = $debugResult."</result>";
    	echo $debugResult;
    }
?>