package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.hibernate.internal.build.AllowSysOut;

public class RedesController {

	//Identificador de Sistema Operacional Operante
	public void identificadorSistemaOperacional() {
		String os = System.getProperty("os.name");
		String arch = System.getProperty("os.arch");
		String version = System.getProperty("os.version");
		System.out.println(os + "v. " + version + "- arch. " + arch);
	}
	
	//Lê os processos ativos
	public void leitorProcessosAtivos(String sistemaOperacional) {
		try {
			if(sistemaOperacional == "Windows" || sistemaOperacional == "windows") {
				sistemaOperacional = "TASKLIST /FO TABLE";
			} else if(sistemaOperacional == "Linux" || sistemaOperacional == "linux") {
				sistemaOperacional = "ps -ef";
			}
			Process P = Runtime.getRuntime().exec(sistemaOperacional);
			InputStream fluxo = P.getInputStream();
			InputStreamReader leitor = new InputStreamReader(fluxo);
			BufferedReader buffer = new BufferedReader(leitor);
			String linha = buffer.readLine();
			while(linha != null) {
				System.out.println(linha);
				linha = buffer.readLine();
			}
			buffer.close();
			leitor.close();
			fluxo.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void callProcess(String process) {
		try {
			Runtime.getRuntime().exec(process);
		} catch (Exception e) {
			String msgErro = e.getMessage();
			if(msgErro.contains("740")) {
				//cmd /c caminho_do_processo
				StringBuffer buffer = new StringBuffer();
				buffer.append("cmd /c");
				buffer.append(" ");
				buffer.append(process);
				try {
					Runtime.getRuntime().exec(buffer.toString());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}else {
				System.err.println(msgErro);
			}
			
		}
		
	}
	
	//Mata/Finaliza o processo pelo nome do processo /cmdNome
	public void finalizarProcessoPorNome(String sistemaOperacional, String nomeProcesso) {
		if(sistemaOperacional == "Windows" || sistemaOperacional == "windows") {
			sistemaOperacional = "TASKKILl /IM";
		} else if(sistemaOperacional == "Linux" || sistemaOperacional == "linux") {
			sistemaOperacional = "ps -ef";
		}
		String cmdNome = sistemaOperacional;
		StringBuffer buffer = new StringBuffer();
		
			//TASKKILL /IM nomedoprocesso.exe
			buffer.append(cmdNome);
			buffer.append(" ");
			buffer.append(nomeProcesso);
		
		callProcess(buffer.toString());
	}
	
	//Mata/Finaliza o processo pelo PID /cmdPid
		public void finalizarProcessoPeloPID(String sistemaOperacional, String PID) {
			if(sistemaOperacional == "Windows" || sistemaOperacional == "windows") {
				sistemaOperacional = "TASKKILl /PID";
			} else if(sistemaOperacional == "Linux" || sistemaOperacional == "linux") {
				sistemaOperacional = "ps -ef";
			}
			
			String cmdPid = "TASKKILL /PID";
			int pid = 0;
			StringBuffer buffer = new StringBuffer();
			
				//TASKKILL /IM nomedoprocesso.exe
			pid = Integer.parseInt(PID);
			buffer.append(cmdPid);
			buffer.append(" ");
			buffer.append(pid);
			
			callProcess(buffer.toString());
		}

	
	
}
