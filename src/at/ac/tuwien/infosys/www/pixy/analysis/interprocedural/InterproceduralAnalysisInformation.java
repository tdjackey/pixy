package at.ac.tuwien.infosys.www.pixy.analysis.interprocedural;

import at.ac.tuwien.infosys.www.pixy.analysis.AnalysisInformation;
import at.ac.tuwien.infosys.www.pixy.analysis.AnalysisNode;
import at.ac.tuwien.infosys.www.pixy.analysis.LatticeElement;
import at.ac.tuwien.infosys.www.pixy.analysis.TransferFunction;
import at.ac.tuwien.infosys.www.pixy.conversion.cfgnodes.AbstractCfgNode;

/**
 * @author Nenad Jovanovic <enji@seclab.tuwien.ac.at>
 */
public class InterproceduralAnalysisInformation extends AnalysisInformation {
    public InterproceduralAnalysisInformation() {
        super();
    }

    // folds all analysis nodes (using recycling) and clears the phi maps
    // (=> saves memory)
    public void foldRecycledAndClean(InterproceduralAnalysis analysis) {
        for (AnalysisNode analysisNode1 : this.map.values()) {
            InterproceduralAnalysisNode analysisNode = (InterproceduralAnalysisNode) analysisNode1;
            LatticeElement foldedValue = analysisNode.computeFoldedValue();
            foldedValue = analysis.recycle(foldedValue);
            analysisNode.setFoldedValue(foldedValue);
            analysisNode.clearPhiMap();
        }
    }

    // note that not all cfg nodes have an associated analysis node:
    // - nodes inside basic blocks
    // - nodes inside function default cfgs
    // for such nodes, you should query the enclosing basic block, or the
    // entry node of the function default cfg; use the appropriate "get"
    // method of CfgNode to retrieve these nodes
    public InterproceduralAnalysisNode getAnalysisNode(AbstractCfgNode cfgNode) {
        return (InterproceduralAnalysisNode) this.map.get(cfgNode);
    }

    public TransferFunction getTransferFunction(AbstractCfgNode cfgNode) {
        AnalysisNode analysisNode = this.getAnalysisNode(cfgNode);
        return analysisNode.getTransferFunction();
    }
}