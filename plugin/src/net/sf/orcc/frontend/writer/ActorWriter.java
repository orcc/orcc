package net.sf.orcc.frontend.writer;

import static net.sf.orcc.ir.IrConstants.KEY_NAME;
import static net.sf.orcc.ir.IrConstants.KEY_SOURCE_FILE;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import net.sf.orcc.OrccException;
import net.sf.orcc.frontend.parser.RVCCalASTParser;
import net.sf.orcc.ir.IrConstants;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.VarDef;
import net.sf.orcc.ir.actor.Actor;
import net.sf.orcc.ir.expr.Util;
import net.sf.orcc.ir.type.BoolType;
import net.sf.orcc.ir.type.IType;
import net.sf.orcc.ir.type.IntType;
import net.sf.orcc.ir.type.ListType;
import net.sf.orcc.ir.type.StringType;
import net.sf.orcc.ir.type.UintType;
import net.sf.orcc.ir.type.VoidType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ActorWriter {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws OrccException {
		Actor actor = new RVCCalASTParser(args[0]).parse();
		new ActorWriter(actor).write(args[1]);
	}

	private Actor actor;

	/**
	 * Creates an actor writer on the given actor.
	 * 
	 * @param actor
	 *            an actor
	 */
	public ActorWriter(Actor actor) {
		this.actor = actor;
	}

	public void write(String outputDir) throws OrccException {
		try {
			OutputStream os = new FileOutputStream(outputDir + File.separator
					+ actor.getName() + "_2.json");
			JSONObject obj = writeActor();
			os.write(obj.toString().getBytes("UTF-8"));
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		} catch (JSONException e) {
			throw new OrccException("JSON error", e);
		}
	}

	private JSONObject writeActor() throws JSONException, OrccException {
		JSONObject obj = new JSONObject();
		obj.put(KEY_SOURCE_FILE, actor.getFile());
		obj.put(KEY_NAME, actor.getName());
		obj.put(IrConstants.KEY_INPUTS, writePorts(actor.getInputs()));
		obj.put(IrConstants.KEY_OUTPUTS, writePorts(actor.getOutputs()));
		obj.put(IrConstants.KEY_STATE_VARS, new JSONArray());
		obj.put(IrConstants.KEY_PROCEDURES, new JSONArray());
		obj.put(IrConstants.KEY_ACTIONS, new JSONArray());
		obj.put(IrConstants.KEY_INITIALIZES, new JSONArray());
		obj.put(IrConstants.KEY_ACTION_SCHED, new JSONArray());
		return obj;
	}

	private JSONArray writeLocation(Location location) {
		JSONArray array = new JSONArray();
		array.put(location.getStartLine());
		array.put(location.getStartColumn());

		// TODO remove when frontend done
		array.put(42);
		array.put(location.getEndColumn());

		return array;
	}

	private JSONArray writePorts(List<VarDef> ports) throws OrccException {
		JSONArray array = new JSONArray();
		for (VarDef varDef : ports) {
			array.put(writeVarDef(varDef));
		}

		return array;
	}

	private Object writeType(IType type) throws OrccException {
		if (type.getType() == IType.BOOLEAN) {
			return BoolType.NAME;
		} else if (type.getType() == IType.STRING) {
			return StringType.NAME;
		} else if (type.getType() == IType.VOID) {
			return VoidType.NAME;
		} else {
			JSONArray array = new JSONArray();
			if (type.getType() == IType.INT) {
				array.put(IntType.NAME);
				int size = Util.evaluateAsInteger(((IntType) type).getSize());
				array.put(size);
			} else if (type.getType() == IType.UINT) {
				array.put(UintType.NAME);
				int size = Util.evaluateAsInteger(((UintType) type).getSize());
				array.put(size);
			} else if (type.getType() == IType.LIST) {
				array.put(ListType.NAME);
				int size = Util.evaluateAsInteger(((ListType) type).getSize());
				array.put(size);
				array.put(writeType(((ListType) type).getElementType()));
			} else {
				throw new OrccException("Invalid type definition: "
						+ type.toString());
			}

			return array;
		}
	}

	private JSONArray writeVarDef(VarDef varDef) throws OrccException {
		JSONArray array = new JSONArray();

		JSONArray details = new JSONArray();
		details.put(varDef.getName());
		details.put(varDef.isAssignable());
		details.put(varDef.isGlobal());
		if (varDef.hasSuffix()) {
			details.put(varDef.getSuffix());
		} else {
			details.put((Object) null);
		}
		details.put(varDef.getIndex());

		// TODO write node id
		details.put(42); // node Id

		array.put(details);
		array.put(writeLocation(varDef.getLoc()));
		array.put(writeType(varDef.getType()));

		// TODO write def-use chains
		array.put(new JSONArray());

		return array;
	}

}
