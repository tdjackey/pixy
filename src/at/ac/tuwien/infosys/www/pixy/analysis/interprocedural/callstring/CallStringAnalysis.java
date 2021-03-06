package at.ac.tuwien.infosys.www.pixy.analysis.interprocedural.callstring;

import at.ac.tuwien.infosys.www.pixy.analysis.AbstractTransferFunction;
import at.ac.tuwien.infosys.www.pixy.analysis.interprocedural.*;
import at.ac.tuwien.infosys.www.pixy.conversion.TacFunction;
import at.ac.tuwien.infosys.www.pixy.conversion.cfgnodes.AbstractCfgNode;
import at.ac.tuwien.infosys.www.pixy.conversion.cfgnodes.Call;

import java.util.List;

/**
 * Base class for analysis using the call string approach of Sharir and Pnueli.
 *
 * Use this if your lattice has infinite breadth.
 *
 * @author Nenad Jovanovic <enji@seclab.tuwien.ac.at>
 */
public class CallStringAnalysis extends AbstractAnalysisType {
    // INPUT ***********************************************************************

    // results from preceding connector computation (for interprocedural
    // propagation)
    ConnectorComputation connectorComp;

// *********************************************************************************
// CONSTRUCTORS ********************************************************************
// *********************************************************************************

    public CallStringAnalysis(ConnectorComputation connectorComp) {
        super();
        this.connectorComp = connectorComp;
    }

// *********************************************************************************
// GET *****************************************************************************
// *********************************************************************************

//  getPropagationContext ***********************************************************

    public AbstractContext getPropagationContext(Call callNode, AbstractContext contextX) {

        CallStringContext context = (CallStringContext) contextX;
        return this.connectorComp.getTargetContext(callNode, context.getPosition());
    }

//  getReverseTargets ***************************************************************

    public List<ReverseTarget> getReverseTargets(TacFunction exitedFunction, AbstractContext contextX) {

        CallStringContext context = (CallStringContext) contextX;
        return this.connectorComp.getReverseTargets(exitedFunction, context.getPosition());
    }

//  *********************************************************************************

    public ConnectorComputation getConnectorComputation() {
        return this.connectorComp;
    }

//  *********************************************************************************
//  OTHER ***************************************************************************
//  *********************************************************************************

    public boolean useSummaries() {
        return false;
    }

    public AbstractInterproceduralAnalysisNode makeAnalysisNode(AbstractCfgNode cfgNode, AbstractTransferFunction tf) {
        return new CallStringAnalysisNode(cfgNode, tf);
    }

    public AbstractContext initContext(AbstractInterproceduralAnalysis analysis) {
        return new CallStringContext(0);
    }
}