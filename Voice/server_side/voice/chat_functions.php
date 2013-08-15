<?php
function bolleanHolder($value){
    if( is_bool($value) ){
        if ( $value ){
            $value = "true";
        }else{
            $value = "false";
        }
    }
    return $value;
}
function dataToXML($input){
    $xml = null;
    $xml .= "<data>";

    foreach ($input as $arr){
        if(is_array($arr)){
            $xml .= "<row>";
            foreach ($arr as $key => $value) {
                $value = bolleanHolder($value);
                $xml .= "<".$key.">"."<![CDATA[".$value."]]>"."</".$key.">";
                //$xml .= '<'.$key.' data="'.$value.'" />'; // wrong with some words
            }
            $xml .= "</row>";
        }
    }

    $xml .= "</data>";
    return $xml;
}

function addDOM($value, $key){
	return "<".$key.">"."<![CDATA[".$value."]]>"."</".$key.">";
}

//DOMHandler("row", 'http://localhost/PHP/getData.php', true for print out);
function XMLReader($rowTag ,$xmlSource, $boolean = false) {

    $doc = new DOMDocument();
    $doc->load($xmlSource);

    $row = $doc->getElementsByTagName($rowTag);
    
    foreach($row as $element){
        foreach($element->childNodes as $node){
            $data[$node->nodeName] = $node->nodeValue;
            echo ($boolean) ? $node->nodeName . " : " . $node->nodeValue . "<br />" : "sad";
        }
    }
    
    return $data;
    

}

// $base = GetFilePath('http://localhost/PHP/getData.php'); and you will get http://localhost/PHP/
function getFilePath_leo($file) {
    $find = '/';
    $after_find = substr(strrchr($file, $find), 1);
    $strlen_str = strlen($after_find);
    $result = substr($file, 0, -$strlen_str);
    return $result;
}

function xmlToArray(DOMNode $node = null)
{
    $result = array();
    $group = array();
    $attrs = null;
    $children = null;
 
    if($node->hasAttributes())
    {
        $attrs = $node->attributes;
        foreach($attrs as $k => $v)
        {
            $result[$v->name] = $v->value;
        }
    }
 
    $children = $node->childNodes;
 
    if(!empty($children))
    {
        if((int)$children->length === 1)
        {
            $child = $children->item(0);
 
            if($child !== null && $child->nodeType === XML_TEXT_NODE)
            {
                $result['#value'] = $child->nodeValue;
                if(count($result) == 1)
                {
                    return $result['#value'];
                }else{
                    return $result;
                }
            }
        }
 
        for($i = 0; $i < (int)$children->length; $i++)
        {
            $child = $children->item($i);
 
            if($child !== null)
            {
                if(!isset($result[$child->nodeName]))
                {
                    $result[$child->nodeName] = xmlToArray($child);
                }else{
                    if(!isset($group[$child->nodeName]))
                    {
                        $result[$child->nodeName] = array($result[$child->nodeName]);
                        $group[$child->nodeName] = 1;
                    }
                    $result[$child->nodeName][] = xmlToArray($child);
                }
            }
        }
    }
    return $result;
}

function stripHTML($input){
    $search = array ('@ ]*?>.*?@si', // Strip out javascript
                 '@<[\/\!]*?[^<>]*?>@si',          // Strip out HTML tags
                 '@([\r\n])[\s]+@',                // Strip out white space
                 '@&(quot|#34);@i',                // Replace HTML entities
                 '@&(amp|#38);@i',
                 '@&(lt|#60);@i',
                 '@&(gt|#62);@i',
                 '@&(nbsp|#160);@i',
                 '@&(iexcl|#161);@i',
                 '@&(cent|#162);@i',
                 '@&(pound|#163);@i',
                 '@&(copy|#169);@i',
                 '@&#(\d+);@e');                    // evaluate as php

    $replace = array ('',
                 '',
                 '\1',
                 '"',
                 '&',
                 '<',
                 '>',
                 ' ',
                 chr(161),
                 chr(162),
                 chr(163),
                 chr(169),
                 'chr(\1)');

    $result = preg_replace($search, $replace, $input);
    return $result;
}
?>
