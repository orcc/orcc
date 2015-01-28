package net.sf.orcc.backends.c.compa

import net.sf.orcc.backends.CommonPrinter
import java.io.File
import net.sf.orcc.util.OrccUtil

class HWConfigPrinter extends CommonPrinter {
	int _nbProcessors
	int _nbActors
	int _ctrl_fifo_size
	int _ctrl_fifo_start_addr
	
	new(int nbProcessors, int nbActors, int ctrl_fifo_size, int ctrl_fifo_start_addr) {
		_nbProcessors = _nbProcessors
		_nbActors = nbActors
		_ctrl_fifo_size = ctrl_fifo_size
		_ctrl_fifo_start_addr = ctrl_fifo_start_addr
	}
	
	def printMbConfigFile(String targetFolder){
		val content = mbConfigFileContent
		val file = new File(targetFolder + File::separator + "config_mb.h")
		
		if(needToWriteFile(content, file)) {
			OrccUtil::printFile(content, file)
			return 0
		} else {
			return 1
		}
	}
	
	def getMbConfigFileContent() '''
		#ifndef CONFIG_MB_H_
		#define CONFIG_MB_H_
		
		#define NB_ACTORS					«_nbActors»
		
		#define CTRL_FIFO_IN_0_SIZE 		«_ctrl_fifo_size»
		#define CTRL_FIFO_IN_0_START_ADDR	«String.format("0x%x",_ctrl_fifo_start_addr)»
		#define CTRL_FIFO_IN_0_RD_IX_ADDR	CTRL_FIFO_IN_0_START_ADDR + CTRL_FIFO_IN_0_SIZE
		#define CTRL_FIFO_IN_0_WR_IX_ADDR	CTRL_FIFO_IN_0_RD_IX_ADDR + 4
		
		#define CTRL_FIFO_OUT_0_SIZE		«_ctrl_fifo_size»
		#define CTRL_FIFO_OUT_0_START_ADDR	«String.format("0x%x",_ctrl_fifo_start_addr + _ctrl_fifo_size + 8)»
		#define CTRL_FIFO_OUT_0_RD_IX_ADDR	CTRL_FIFO_OUT_0_START_ADDR + CTRL_FIFO_OUT_0_SIZE
		#define CTRL_FIFO_OUT_0_WR_IX_ADDR	CTRL_FIFO_OUT_0_RD_IX_ADDR + 4
		
		#endif /* CONFIG_MB_H_ */
	'''
	
}