#header()
#set( $name = $classObject.getName() )package ${aib.getRootPackageName(true)}.#getBOPackageName();

import java.util.*;

import com.cloudmigrate.common.bo.*;
import com.cloudmigrate.foundational.common.*;

#set( $imports = [ "#getPrimaryKeyPackageName()", "#getBOPackageName()" ] )
#importStatements( $imports )

/**
 * Interface for business entity ${name}.
 * <p>
 * @author    $aib.getAuthor()
 *  
 */
// AIB : \#getInterfaceDecl()
#getInterfaceDecl()
// ~AIB

    /** 
     * Returns the ${name}PrimaryKey
     * @return ${name}PrimaryKey   
     */
    public ${name}PrimaryKey get${name}PrimaryKey();

// AIB : \#getBOAccessorMethodDeclarations()
#getBOAccessorMethodDeclarations()
// ~AIB

}


