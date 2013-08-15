<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>

<form action="chat_setChat.php" method="post">
  <input name = "fuidstr" value="19983,19991"> <br />
  <input name = "room" value="jenova, maleo"> <br />
  <input name = "fuid" value="19983"> <br />
  <input name = "said" value="中文"> <br />
  <input name = "type" value="text"> <br />
  <input name = "dateline" value="1328268254"> <br />
  <INPUT type="submit" value="Send">
</form>

<br />

<form action="chat_setData.php" method="post">
  <input name = "action" value="addContact"> <br />
  
  <input name = "uid" value="19983"> <br />
  <input name = "fuid" value="19991"> <br />
  <input name = "status" value="1"> <br />
  <input name = "fusername" value="jenova"> <br />
  <input name = "gid" value="0"> <br />
  <input name = "dateline" value="1324538085"> <br />
  <INPUT type="submit" value="Send">
</form>

<form action="chat_setData.php" method="post">
  <input name = "action" value="updateSpacenote"> <br />
  
  <input name = "uid" value="19983"> <br />
  <input name = "spacenote" value="test123"> <br />
  <INPUT type="submit" value="Send">
</form>


<form action="chat_setData.php" method="post">
  <input name = "action" value="deleteChatroom"> <br />
  
  <input name = "fuidstr" value="19983,19991"> <br />
  <INPUT type="submit" value="Send">
</form>


<form action="chat_setData.php" method="post">
  <input name = "action" value="restoreChatroom"> <br />
  
  <input name = "fuidstr" value="19983,19991"> <br />
  <INPUT type="submit" value="Send">
</form>

<form action="chat_setData.php" method="post">
  <input name = "action" value="deleteChat"> <br />
  
  <input name = "_id" value="13"> <br />
  <INPUT type="submit" value="Send">
</form>


<form action="chat_setData.php" method="post">
  <input name = "action" value="restoreChat"> <br />
  
  <input name = "_id" value="13"> <br />
  <INPUT type="submit" value="Send">
</form>