<?php


/**
 * Description of Util
 *
 * @author abraham
 */
namespace common\components;

use yii\helpers\VarDumper;


class Utileria {
	
	    
    public static function array_combinar($array1,$array2){

        $array_final = [];
        $array_indices = [];

        if (count($array1) > count($array2)){
           $array_final = array_chunk($array1,count($array2));
           $array_indices = $array2;
        }else{
           $array_final = array_chunk($array2,count($array1));
           $array_indices = $array1;
        }

        $array_out = [];
        foreach ($array_final as $key1=>$value1) {
            foreach ($value1 as $key2=>$value2) {
                $val= $array_final[$key1][$key2];
                $array_out[$key1][$array_indices[$key2]] = $value2;
            }
        }
        return $array_out;
    }
	
	

	public static function getCSV($path)
	{
		$fila = 1;
		$lista  = [];

		if (($gestor = fopen("$path", "r")) !== FALSE) {
		 while (($datos = fgetcsv($gestor, 1000, ",")) !== FALSE) {
			$elemento=[];
		        $numero = count($datos);
		        $fila++;
		        for ($c=0; $c < $numero; $c++) {
		            array_push($elemento,$datos[$c]);
		        }
			array_push($lista,$elemento);
		    }
		    fclose($gestor);
		}

		return $lista;
	}


    public static function array_in_column_str_replace(&$array = NULL,$column =NULL,$pattern = NULL,$str_replace = NULL) {
        foreach ($array as $key => $value) {
            $value_column = $value[$column];
            $value_final = str_replace($pattern, "", $value_column);
            $array[$key][$column] = $value_final;
        }
    }


    public static  function array_to_xml($array)
    {
        ob_start();
        foreach ($array as $key => $value) {
            if (!is_array($value)) {
                echo "<$key>".$value."</$key>\n";
            }else{
                echo "<$key>";
                echo self::array_to_xml($value);
                echo "</$key>\n";
            }

        }
        $salida = ob_get_contents();
        
        ob_end_clean();
        
        return $salida;
        
    }
    
    
    public static function dump($var){
        
        VarDumper::dump($var, 10, 1);
        
    }

    public static function array_count_nulls($array = array()){
        $count = 0;
        foreach ($array as $value) {
            if ($value == NULL) {
                $count++;
            }
        }
    return $count;
    }
    
    public static function array_remove_column_nulls(&$array = array()){
        
        foreach ($array as $key=>$column) {
            if (count($column) == static::array_count_nulls($column)) {
                unset($array[$key]);
            }
        }
   
    }
    

    public static  function array_remove_first(&$array = NULL) {
        foreach ($array as $key => $value) {
            unset($array[$key]);
            break;
        }
       
    }

    public static  function array_get_first($array = NULL) {
        $row = NULL;
        foreach ($array as $key => $value) {
            $row = $array[$key];
            break;
        }
        return $row;
    }
    
    public static  function array_change_key_columns($array = NULL,$array_keys) {
        $row = NULL;
        foreach ($array as $key => $value) {
            $array[$key] = array_combine($array_keys,$value);
        }
        return $array;
    }
    
    
    public static  function get_DATE($_date = ""){
        $final_date = ""; 


        if ($date = date_create_from_format('d/m/Y', $_date)) {
            $final_date =  date_format($date, 'Y/m/d');

        }
        if ($date = date_create_from_format('Y/m/d', $_date)) {
            $final_date =  date_format($date, 'Y/m/d');

        }

        return $final_date;
    }

    
}
